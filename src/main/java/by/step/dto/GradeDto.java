package by.step.dto;

import by.step.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradeDto {
    private int studentId;
    private String studentName;
    private int score;
    private Subject subject;
}
