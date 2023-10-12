package kr.co.dcon.taskserver.auth.service;

import kr.co.dcon.taskserver.auth.dto.RealmInfoDTO;
import kr.co.dcon.taskserver.auth.dto.UserSimpleDTO;
import kr.co.dcon.taskserver.auth.dto.UserWorkDTO;
import kr.co.dcon.taskserver.common.constants.UserOtherClaim;
import kr.co.dcon.taskserver.common.util.TokenUtil;
import kr.co.dcon.taskserver.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
@Slf4j
@Component("keyCloakCurrentUserService")
public class KeyCloakCurrentUserServiceImpl  implements CurrentUserService{

	@Autowired
	UserMapper userMapper;

	private RealmInfoDTO realmInfoDTO;

	@Override
	public UserWorkDTO getCurrentUser() {
		AccessToken accessToken = TokenUtil.getAccessToken();
        return buildUserWork(accessToken);
	}

	@Override
	public RealmInfoDTO getRealmInfo() {
		this.realmInfoDTO = this.realmInfoDTO == null ? userMapper.selectKeycloakRealmInfo() : this.realmInfoDTO;
		log.info("realmINfo" + realmInfoDTO);
		return this.realmInfoDTO;
	}

	@Override
	public void setRealmInfo() {
		this.realmInfoDTO = this.realmInfoDTO == null ? userMapper.selectKeycloakRealmInfo() : this.realmInfoDTO;
	}


	private UserWorkDTO buildUserWork(AccessToken accessToken) {
		Map<String, Object> otherClaims = accessToken.getOtherClaims();
		UserWorkDTO userWork = new UserWorkDTO();
		userWork.setUserId(accessToken.getSubject());
		userWork.setUserEmail(accessToken.getEmail());
		userWork.setUserName(accessToken.getEmail());
		userWork.setUserName((String)otherClaims.get(UserOtherClaim.USER_NAME));
		userWork.setAuth((String)otherClaims.get(UserOtherClaim.AUTH));
		userWork.setPhone((String) otherClaims.get(UserOtherClaim.PHONE));
		userWork.setCompany((String) otherClaims.get(UserOtherClaim.COMPANY));
		userWork.setUseYn((String) otherClaims.get(UserOtherClaim.USE_YN));
		userWork.setLocale((String) otherClaims.get(UserOtherClaim.USER_LOCALE));
		return userWork;
	}

}
