package telephoneregister.numbertype;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NumberTypeRepository extends JpaRepository<NumberType, Long> {
    @Query("SELECT n FROM NumberType n WHERE n.typeName=:phoneNumberTypeName")
    NumberType find(String phoneNumberTypeName);
}