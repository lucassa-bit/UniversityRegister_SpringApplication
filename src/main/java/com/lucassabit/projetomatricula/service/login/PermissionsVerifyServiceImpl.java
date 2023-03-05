package com.lucassabit.projetomatricula.service.login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucassabit.projetomatricula.dto.send.UserSendDTO;
import com.lucassabit.projetomatricula.enumerators.UserType;
import com.lucassabit.projetomatricula.error.AccessDeniedException;
import com.lucassabit.projetomatricula.error.login.DoesntExistUserTypeException;
import com.lucassabit.projetomatricula.error.login.UserDoestExistException;
import com.lucassabit.projetomatricula.repository.SecretaryRepository;

@Service
public class PermissionsVerifyServiceImpl implements PermissionsVerifyService {

	@Autowired
	private SecretaryRepository usuarioRepository;
	@Autowired
	private UserService usuarioServices;

	@Override
	public boolean existUserBylogin(String login) {
		return usuarioRepository.existsByLoginIgnoreCaseOrderByNameAsc(login);
	}

	@Override
	public UserSendDTO PermissionVerify(String loginUsuario, List<UserType> permitidos)
			throws UserDoestExistException, AccessDeniedException, DoesntExistUserTypeException {
		UserSendDTO usuario = usuarioServices.getUsuarioByLogin(loginUsuario);

		if (!permitidos.contains(usuario.getUserType()))
			throw new AccessDeniedException();

		return usuario;
	}

}
