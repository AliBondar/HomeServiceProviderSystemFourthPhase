package org.example.entity.users;



import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.base.domain.BaseEntity;
import org.example.entity.Wallet;
import org.example.entity.users.enums.UserStatus;
import java.time.LocalDate;


@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
public class User extends BaseEntity<Long> {

    @OneToOne
    Wallet wallet;

    String firstName;

    String lastName;

    @Email
    String email;

    String password;

    @Enumerated(EnumType.STRING)
    UserStatus userStatus;

    LocalDate signUpDate;

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userStatus=" + userStatus +
                ", signUpDate=" + signUpDate +
                '}';
    }
}
