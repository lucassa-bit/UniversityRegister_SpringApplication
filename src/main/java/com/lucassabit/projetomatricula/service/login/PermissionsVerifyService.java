package com.lucassabit.projetomatricula.service.login;

import java.util.List;

import com.lucassabit.projetomatricula.dto.send.UserSendDTO;
import com.lucassabit.projetomatricula.enumerators.UserType;
import com.lucassabit.projetomatricula.error.AccessDeniedException;
import com.lucassabit.projetomatricula.error.login.DoesntExistUserTypeException;
import com.lucassabit.projetomatricula.error.login.UserDoestExistException;

public interface PermissionsVerifyService {
	public UserSendDTO PermissionVerify(String loginUsuario, List<UserType> permitidos)
			throws UserDoestExistException, AccessDeniedException, DoesntExistUserTypeException;

	public boolean existUserBylogin(String login);

}
