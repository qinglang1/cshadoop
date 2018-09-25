package domain;

import java.io.Serializable;

/**
 * Person
 */
public class Person implements Serializable{

	public static final long serialVersionUID = 4358131788078372361L;

	public Person(String name){
		this.name = name ;
	}
	private Integer id ;
	private String name ;
	private int age ;
	private boolean married ;

	private Person friend ;

	private transient Person diren ;

	public Person getDiren() {
		return diren;
	}

	public void setDiren(Person diren) {
		this.diren = diren;
	}

	public Person getFriend() {
		return friend;
	}

	public void setFriend(Person friend) {
		this.friend = friend;
	}

	public boolean isMarried() {
		return married;
	}

	public void setMarried(boolean married) {
		this.married = married;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}