package ez.clap.gestionetudiant_aql.entities;

import java.io.Serializable;

public class Grade implements Serializable {
    private double maxPoints;
    private double points;

    public Grade(){
        this.setGrade(0);
    }
    public Grade(double gradeInPercent){
        this.setGrade(gradeInPercent);
    }

    public Grade(double points, double maxPoints){
        this.setGrade(points, maxPoints);
    }

    public Character getGrade(){
        if(this.getGradeInPercent() >= 90)
            return 'A';
        else if (this.getGradeInPercent() >= 80) {
            return 'B';
        } else if (this.getGradeInPercent() >= 70) {
            return 'C';
        } else if (this.getGradeInPercent() >= 60) {
            return 'D';
        }else if (this.getGradeInPercent() >= 50){
            return 'E';
        }else{
            return 'F';
        }
    }

    // TODO: Utiliser la fonction dans le manage grade
    public String getGradeInPercentString(){
        return (this.points / this.maxPoints) + "%";
    }

    public double getGradeInPercent(){
        return this.points/this.maxPoints*100;
    }

    public double getMaxPoints() {
        return maxPoints;
    }

    public double getPoints() {
        return points;
    }

    public void setGrade(double points, double maxPoints) {
        if(points > maxPoints)
            this.points = this.maxPoints = maxPoints;
        else{
            this.points = points < 0 ? 0 : points;
            this.maxPoints = maxPoints < 0 ? 0 : maxPoints;
        }
    }

    public void setGrade(double gradeInPercent){
        this.setGrade(gradeInPercent, 100);

    }
}
