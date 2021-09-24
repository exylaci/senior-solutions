package mentortools.student;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "students")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Tag(name = "Operations on student.")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_name")
    @Schema(example = "John Doe", description = "Name of the student.")
    private String name;

    @Column(name = "email_address", nullable = false)
    @Schema(example = "john.doe@mailserver.hu", description = "Email address of the student.")
    private String email;

    @Column(name = "github_username")
    @Schema(example = "johndoe", description = "GitHub user name of the student.")
    private String gitHubUserName;

    @Column(name = "comment")
    @Schema(example = "It's a comment about him.", description = "Comment related to the student.")
    private String comment;

    public Student(String name, String email, String gitHubUserName, String comment) {
        this.name = name;
        this.email = email;
        this.gitHubUserName = gitHubUserName;
        this.comment = comment;
    }
}