package org.javaprotrepticon.android.kotactik.storage.model;

import java.sql.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Profile {
	
	@DatabaseField(id = true)
	private Integer id;
	
	@DatabaseField
	private String firstName;
	
	@DatabaseField
	private String lastName;
	
	@DatabaseField
	private String maidenName;
	
	@DatabaseField
	private Integer sex;
	
	@DatabaseField
	private Integer relation;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private User relationPartner;
	
	@DatabaseField
	private Date birthDate;
	
	@DatabaseField
	private Integer birthDateVisibility;
	
	@DatabaseField
	private String homeTown;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private Country country;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private City city;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private NameRequest nameRequest;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMaidenName() {
		return maidenName;
	}

	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getRelation() {
		return relation;
	}

	public void setRelation(Integer relation) {
		this.relation = relation;
	}

	public User getRelationPartner() {
		return relationPartner;
	}

	public void setRelationPartner(User relationPartner) {
		this.relationPartner = relationPartner;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Integer getBirthDateVisibility() {
		return birthDateVisibility;
	}

	public void setBirthDateVisibility(Integer birthDateVisibility) {
		this.birthDateVisibility = birthDateVisibility;
	}

	public String getHomeTown() {
		return homeTown;
	}

	public void setHomeTown(String homeTown) {
		this.homeTown = homeTown;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public NameRequest getNameRequest() {
		return nameRequest;
	}

	public void setNameRequest(NameRequest nameRequest) {
		this.nameRequest = nameRequest;
	}
	
}
