package api.tutoringschool.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "tasks")
@Entity
public class Task implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "subject_id", nullable = false)
    private UUID subjectId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "images")
    private String[] images;

    @Column(name = "deadline_date", nullable = false)
    private Date deadlineDate;

    @Column(name = "is_concluded", nullable = false)
    private boolean isConcluded = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student = new Student();

    public Task() {
    }

    public Task(UUID subjectId, String title, String description, String[] images, Date deadlineDate,
            boolean isConcluded, Student student) {
        this.subjectId = subjectId;
        this.title = title;
        this.description = description;
        this.images = images;
        this.deadlineDate = deadlineDate;
        this.isConcluded = isConcluded;
        this.student = student;
    }

    public UUID getId() {
        return id;
    }

    public UUID getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(UUID subjectId) {
        this.subjectId = subjectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public boolean isConcluded() {
        return isConcluded;
    }

    public void setConcluded(boolean isConcluded) {
        this.isConcluded = isConcluded;
    }

    public UUID getStudentId() {
        return student.getId();
    }

    public void setStudentId(UUID studentId) {
        this.student.setId(studentId);
    }
}
