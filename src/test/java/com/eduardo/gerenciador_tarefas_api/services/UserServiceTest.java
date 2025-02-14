package com.eduardo.gerenciador_tarefas_api.services;

import com.eduardo.gerenciador_tarefas_api.exceptions.UserAlreadyExistsException;
import com.eduardo.gerenciador_tarefas_api.models.User;
import com.eduardo.gerenciador_tarefas_api.models.UserRequestDTO;
import com.eduardo.gerenciador_tarefas_api.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@DisplayName("Teste de unidade para as regras de Usuário")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("Deve listar usuários paginados")
    void getAll() {
        // String email, String name, String profile, String avatar
        UserRequestDTO user1 = new UserRequestDTO("email1@dev.com", "Dev 1", "USER", "avatar1");
        UserRequestDTO user2 = new UserRequestDTO("email2@dev.com", "Dev 2", "USER", "avatar2");
        List<User> users = new ArrayList<>();
        users.add(new User(user1));
        users.add(new User(user2));
        Pageable pageable = Pageable.ofSize(10);
        Page<User> userPage = new PageImpl<>(users, pageable, users.size());

        Mockito.when(userRepository.findAll(pageable)).thenReturn(userPage);

        Page<User> result = this.userService.getAll(pageable);

        Assertions.assertEquals(users.size(), result.getContent().size());
        Assertions.assertEquals(users.getFirst().getEmail(), "email1@dev.com");
        Assertions.assertEquals(users.get(1).getEmail(), "email2@dev.com");
        Mockito.verify(this.userRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Deve criar um usuário")
    void create() {
        UserRequestDTO userDTO = new UserRequestDTO("email1@dev.com", "Dev 1", "USER", "avatar1");
        User user = new User(userDTO);
        user.setCreatedAt(LocalDateTime.now());
        user.setId("PleaseStandUpPleaseStandUp");

        Mockito.when(this.userRepository.findByEmail(userDTO.email())).thenReturn(null);
        Mockito.when(this.userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User userCreated = this.userService.create(userDTO);

        Assertions.assertNotNull(userCreated.getId());
        Assertions.assertEquals(userCreated.getEmail(), userDTO.email());
        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(userDTO.email());
        Mockito.verify(this.userRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }

    @Test
    @DisplayName("Deve gerar uma exceção de usuário existente")
    void createWithUserAlreadyExisting() {
        UserRequestDTO userDTO = new UserRequestDTO("email1@dev.com", "Dev 1", "USER", "avatar1");
        User user = new User(userDTO);
        user.setCreatedAt(LocalDateTime.now());
        user.setId("PleaseStandUpPleaseStandUp");

        Mockito.when(this.userRepository.findByEmail(userDTO.email())).thenReturn(user);

        Throwable throwable = Assertions.assertThrows(UserAlreadyExistsException.class, () -> {
            this.userService.create(userDTO);
        });

        Assertions.assertEquals(throwable.getClass(), UserAlreadyExistsException.class);
        Assertions.assertEquals(throwable.getMessage(), "Esse usuário já existe!");
        Mockito.verify(this.userRepository, Mockito.times(1)).findByEmail(userDTO.email());
        Mockito.verify(this.userRepository, Mockito.times(0)).save(Mockito.any(User.class));
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}