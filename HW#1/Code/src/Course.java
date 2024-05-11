/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

public class Course {
    private String name;
    private String department;
    private int code;
    private byte section;
    private String instructor;

    /**
     * Constructor for Course class.
     *
     * @param name Name of the course.
     * @param department Department of the course.
     * @param code Code of the course.
     * @param section section of the course.
     * @param instructor Instructor of the course.
     */
    public Course(String name, String department, int code,
      byte section, String instructor) {
        this.name = name;
        this.department = department;
        this.code = code;
        this.section = section;
        this.instructor = instructor;
    }

    /**
     * Clones the current Course class.
     *
     * @return Returns current Course as an Object.
     */
    public Object clone() {
        return (Object)(new Course(name,department,code,section,
          instructor));
    }

    /**
     * Checks if Course has same content as an object.
     *
     * @param obj Object to be compared.
     * @return Returns a boolean symbolizing if Course equals to the Object.
     */
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } // Checks memory reference.
        if (obj instanceof Course) {
            Course other = (Course)obj; // Casts obj into Course.
            // Compare their values.
            return this.getName().equals(other.getName()) &&
               this.getDepartment().equals(other.getDepartment()) &&
               this.getCode() == other.getCode() &&
               this.getSection() == other.getSection() &&
               this.getInstructor().equals(other.getInstructor());
        }
        return false;
    }

    //Accessors

    /**
     * Gets name of the Course.
     *
     * @return Returns course name as a String.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets department of the course.
     *
     * @return Returns department as a String.
     */
    public String getDepartment() {
        return department;
    }

    /**
     * Gets the code of the Course.
     *
     * @return Returns an int containing the code.
     */
    public int getCode() {
        return code;
    }

    /**
     * Gets the section of the Course.
     *
     * @return Returns an byte containing the section.
     */
    public byte getSection() {
        return section;
    }

    /**
     * Gets the instructor of the Course.
     *
     * @return Returns the instructor's name as a String.
     */
    public String getInstructor() {
        return instructor;
    }

    /**
     * Gets section in a printable String format.
     *
     * @return Returns section as String.
     */
    public String getPrintableSection() {
        if (section < 10) {
            return "0" + section;
        }
        return section + "";
    }

    //Mutator

    /**
     * Change the Course name.
     *
     * @param name The new course name as a String.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Change the department of the Course.
     *
     * @param department The new department as a String.
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * Change the code of the Course.
     *
     * @param code The new code as an int.
     * @throws NegativeNumberException Throws an exception if code is negative.
     * @throws DepartmentCodeException Indicates that code isn't 3 digit.
     */
    public void setCode(int code) throws NegativeNumberException,
      DepartmentCodeException {
        if (code < 0) {
            throw new NegativeNumberException();
        } else if (code > 999 || code < 100) {
            throw new DepartmentCodeException();
        } else {
            this.code = code;
        }
    }

    /**
     * Change the section of the Course.
     *
     * @param section The new section as a byte.
     * @throws NegativeNumberException Throws an exception if section is
     *   negative.
     */
    public void setSection(byte section) throws NegativeNumberException {
        if (section < 0) {
            throw new NegativeNumberException();
        } else {
            this.section = section;
        }
    }

    /**
     * Change the instructor of the Course.
     *
     * @param instructor The name of new instructor as a String.
     */
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
}
