package by.step.model;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student {
    private int id;
    private String name;
    private String email;
}
