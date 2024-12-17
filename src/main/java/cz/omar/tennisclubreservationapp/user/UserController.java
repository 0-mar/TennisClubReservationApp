package cz.omar.tennisclubreservationapp.user;

import cz.omar.tennisclubreservationapp.user.dto.UserCreateDto;
import cz.omar.tennisclubreservationapp.user.dto.UserDto;
import cz.omar.tennisclubreservationapp.user.dto.UserUpdateDto;
import cz.omar.tennisclubreservationapp.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserDto createUser(@Valid @RequestBody UserCreateDto userCreateDto) {
        return userService.create(userCreateDto);
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        return userService.get(userId);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAll();
    }

    @PutMapping("/{userId}")
    public UserDto updateUser(@PathVariable Long userId, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        userUpdateDto.setId(userId);
        return userService.update(userUpdateDto);
    }

    @DeleteMapping("/{userId}")
    public UserDto deleteUser(@PathVariable Long userId) {
        return userService.delete(userId);
    }
}
