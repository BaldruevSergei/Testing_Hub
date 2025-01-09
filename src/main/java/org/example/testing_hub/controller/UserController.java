import org.example.testing_hub.dto.UserUpdateDTO;
import org.example.testing_hub.entity.Admin;
import org.example.testing_hub.entity.Student;
import org.example.testing_hub.entity.Teacher;
import org.example.testing_hub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return userService.getAllStudents();
    }

    @GetMapping("/teachers")
    public List<Teacher> getAllTeachers() {
        return userService.getAllTeachers();
    }

    @GetMapping("/admins")
    public List<Admin> getAllAdmins() {
        return userService.getAllAdmins();
    }

    @GetMapping("/{id}")
    public Object getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/students")
    public Student createStudent(@RequestBody Student student) {
        return (Student) userService.createUser(student);
    }

    @PostMapping("/teachers")
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        return (Teacher) userService.createUser(teacher);
    }

    @PostMapping("/admins")
    public Admin createAdmin(@RequestBody Admin admin) {
        return (Admin) userService.createUser(admin);
    }

    @PutMapping("/{id}")
    public Object updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO updateUserDto) {
        return userService.updateUser(id, updateUserDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
