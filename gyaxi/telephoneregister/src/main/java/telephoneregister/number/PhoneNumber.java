package telephoneregister.number;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumber {
    @Enumerated(EnumType.STRING)
    private AccessType access;
    private String phoneNumber;

    public PhoneNumber(String access, String phoneNumber) {
        this.access = AccessType.valueOf(access);
        this.phoneNumber = phoneNumber;
    }
}