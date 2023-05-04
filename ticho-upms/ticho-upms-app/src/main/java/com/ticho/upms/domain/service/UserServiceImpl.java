package com.ticho.upms.domain.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ticho.boot.log.util.IpUtil;
import com.ticho.boot.redis.util.RedisUtil;
import com.ticho.boot.view.core.BizErrCode;
import com.ticho.boot.view.core.PageResult;
import com.ticho.boot.view.util.Assert;
import com.ticho.boot.web.util.valid.ValidGroup;
import com.ticho.boot.web.util.valid.ValidUtil;
import com.ticho.common.security.dto.SecurityUser;
import com.ticho.common.security.util.UserUtil;
import com.ticho.upms.application.service.UserService;
import com.ticho.upms.domain.handle.UpmsHandle;
import com.ticho.upms.domain.repository.TenantRepository;
import com.ticho.upms.domain.repository.UserRepository;
import com.ticho.upms.domain.repository.UserRoleRepository;
import com.ticho.upms.infrastructure.core.enums.TenantStatus;
import com.ticho.upms.infrastructure.core.enums.UserStatus;
import com.ticho.upms.infrastructure.core.util.CaptchaUtil;
import com.ticho.upms.infrastructure.entity.User;
import com.ticho.upms.infrastructure.entity.UserRole;
import com.ticho.upms.interfaces.assembler.UserAssembler;
import com.ticho.upms.interfaces.dto.UserDTO;
import com.ticho.upms.interfaces.dto.UserPasswordDTO;
import com.ticho.upms.interfaces.dto.UserRoleDTO;
import com.ticho.upms.interfaces.dto.UserSignUpDTO;
import com.ticho.upms.interfaces.query.UserAccountQuery;
import com.ticho.upms.interfaces.query.UserQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户信息 服务实现
 *
 * @author zhajianjun
 * @date 2022-10-13 09:08
 */
@Service
@Slf4j
public class UserServiceImpl extends UpmsHandle implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired(required = false)
    private HttpServletRequest request;

    @Autowired(required = false)
    private HttpServletResponse response;

    @Autowired
    private RedisUtil<String, String> redisUtil;

    @Override
    public void signUp(UserSignUpDTO userSignUpDTO) {
        ValidUtil.valid(userSignUpDTO);
        String tenantId = userSignUpDTO.getTenantId();
        String username = userSignUpDTO.getUsername();
        String password = userSignUpDTO.getPassword();
        boolean exists = tenantRepository.exists(tenantId, Collections.singletonList(TenantStatus.NORMAL.code()));
        Assert.isTrue(exists, BizErrCode.FAIL, "租户不存在或者状态异常");
        User user = new User();
        user.setTenantId(tenantId);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setStatus(UserStatus.NOT_ACTIVE.code());
        UserAccountQuery accountDTO = UserAssembler.INSTANCE.entityToAccount(user);
        preCheckRepeatUser(accountDTO, null);
        Assert.isTrue(userRepository.save(user), BizErrCode.FAIL, "注册失败");
    }

    @Override
    public void confirm(String username) {
        Assert.isNotBlank(username, BizErrCode.PARAM_ERROR, "账户不能为空");
        SecurityUser securityUser = UserUtil.getCurrentUser();
        String tenantId = securityUser.getTenantId();
        User user = userRepository.getByUsername(tenantId, username);
        User updateEntity = new User();
        updateEntity.setId(user.getId());
        updateEntity.setStatus(UserStatus.NORMAL.code());
        Assert.isTrue(userRepository.updateById(updateEntity), BizErrCode.FAIL, "确认失败");
    }

    @Override
    public void save(UserDTO userDTO) {
        ValidUtil.valid(userDTO, ValidGroup.Add.class);
        String password = userDTO.getPassword();
        userDTO.setPassword(passwordEncoder.encode(password));
        User user = UserAssembler.INSTANCE.dtoToEntity(userDTO);
        UserAccountQuery accountDTO = UserAssembler.INSTANCE.entityToAccount(user);
        preCheckRepeatUser(accountDTO, null);
        Assert.isTrue(userRepository.save(user), BizErrCode.FAIL, "保存失败");
    }

    @Override
    public void removeById(Long id) {
        User user = userRepository.getById(id);
        Assert.isNotNull(user, BizErrCode.FAIL, "注销失败,用户不存在");
        // 账户注销
        user.setStatus(4);
        boolean b = userRepository.updateById(user);
        Assert.isNotNull(b, BizErrCode.FAIL, "注销失败");
    }

    @Override
    public void updateById(UserDTO userDTO) {
        ValidUtil.valid(userDTO, ValidGroup.Upd.class);
        userDTO.setPassword(null);
        User user = UserAssembler.INSTANCE.dtoToEntity(userDTO);
        UserAccountQuery accountDTO = UserAssembler.INSTANCE.entityToAccount(user);
        preCheckRepeatUser(accountDTO, userDTO.getId());
        Assert.isTrue(userRepository.updateById(user), BizErrCode.FAIL, "修改失败");
    }

    @Override
    public UserDTO getById(Long id) {
        User user = userRepository.getById(id);
        return UserAssembler.INSTANCE.entityToDto(user);
    }

    @Override
    public PageResult<UserDTO> page(UserQuery query) {
        // @formatter:off
        query.checkPage();
        Page<User> page = PageHelper.startPage(query.getPageNum(), query.getPageSize());
        userRepository.list(query);
        List<UserDTO> userDTOs = page.getResult()
            .stream()
            .map(UserAssembler.INSTANCE::entityToDto)
            .collect(Collectors.toList());
        return new PageResult<>(page.getPageNum(), page.getPageSize(), page.getTotal(), userDTOs);
        // @formatter:on
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindRole(UserRoleDTO userRoleDTO) {
        // @formatter:off
        ValidUtil.valid(userRoleDTO);
        Long userId = userRoleDTO.getUserId();
        List<Long> roleIds = userRoleDTO.getRoleIds();
        userRoleRepository.removeByUserId(userId);
        List<UserRole> userRoles = roleIds
            .stream()
            .map(x-> convertToUserRole(userId, x))
            .collect(Collectors.toList());
        userRoleRepository.saveBatch(userRoles);
        // @formatter:on
    }

    @Override
    public void updatePassword(UserPasswordDTO userPasswordDTO) {
        // @formatter:off
        ValidUtil.valid(userPasswordDTO);
        Long id = userPasswordDTO.getId();
        String password = userPasswordDTO.getPassword();
        String passwordNew = userPasswordDTO.getPasswordNew();
        User queryUser = userRepository.getById(id);
        Assert.isNotEmpty(queryUser, BizErrCode.FAIL, "用户不存在");
        String encodedPassword = queryUser.getPassword();
        SecurityUser loginUser = UserUtil.getCurrentUser();
        // 非管理员用户，只能修改自己的用户
        if (!UserUtil.isAdmin(loginUser)) {
            Assert.isTrue(UserUtil.isSelf(queryUser, loginUser), BizErrCode.FAIL, "只能修改自己的密码");
        }
        boolean matches = passwordEncoder.matches(password, encodedPassword);
        Assert.isTrue(matches, BizErrCode.FAIL, "密码错误");
        String encodedPasswordNew = passwordEncoder.encode(passwordNew);
        User user = new User();
        user.setId(queryUser.getId());
        user.setPassword(encodedPasswordNew);
        // 更新密码
        boolean update = userRepository.updateById(user);
        Assert.isTrue(update, BizErrCode.FAIL, "更新密码失败");
    }

    @Override
    public void verifyByCode() {
        String ip = IpUtil.preIp(request);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        try (OutputStream out = response.getOutputStream()) {
            CaptchaUtil captchaUtils = new CaptchaUtil();
            String result = captchaUtils.getCode();
            //ip + code
            redisUtil.vSet(ip + " code", result, 60, TimeUnit.SECONDS);
            BufferedImage buffImg = captchaUtils.getBuffImg();
            ImageIO.write(buffImg, "png", out);
        } catch (Exception e) {
            log.error("获取验证码失败，error {}", e.getMessage(), e);
        }
    }

    private UserRole convertToUserRole(Long userId, Long roleId) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(roleId);
        return userRole;
    }

    /**
     * 保存或者修改用户信息重复数据判断，用户名称、邮箱、手机号保证其唯一性
     *
     * @param userAccountQuery 用户登录账号信息
     */
    private void preCheckRepeatUser(UserAccountQuery userAccountQuery, Long updateId) {
        String username = userAccountQuery.getUsername();
        String email = userAccountQuery.getEmail();
        String mobile = userAccountQuery.getMobile();
        List<User> users = userRepository.getByAccount(userAccountQuery);
        boolean isUpdate = Objects.nonNull(updateId);
        for (User item : users) {
            Long itemId = item.getId();
            if (isUpdate && Objects.equals(updateId, itemId)) {
                continue;
            }
            String itemUsername = item.getUsername();
            String itemMobile = item.getMobile();
            String itemEmail = item.getEmail();
            // 用户名重复判断
            Assert.isTrue(!Objects.equals(username, itemUsername), BizErrCode.FAIL, "该用户名已经存在");
            // 手机号码重复判断
            Assert.isTrue(!Objects.equals(mobile, itemMobile), BizErrCode.FAIL, "该手机号已经存在");
            // 邮箱重复判断
            Assert.isTrue(!Objects.equals(email, itemEmail), BizErrCode.FAIL, "该邮箱已经存在");
        }
    }

}
