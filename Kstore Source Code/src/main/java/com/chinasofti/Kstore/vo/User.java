package com.chinasofti.Kstore.vo;


public class User {
private int user_id;
private String name;
private String gender;
private String password;
private String email;
private String phone_num;
private String profile_photo;
private String birthday;
private String province;
private String city ;
private String district;
private String street;
private float faith_value;
public String getProvince() {
	return province;
}
public void setProvince(String province) {
	this.province = province;
}

public int getUser_id() {
	return user_id;
}
public void setUser_id(int user_id) {
	this.user_id = user_id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}

public String getBirthday() {
	return birthday;
}
public void setBirthday(String birthday) {
	this.birthday = birthday;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public String getDistrict() {
	return district;
}
public void setDistrict(String district) {
	this.district = district;
}
public String getStreet() {
	return street;
}
public void setStreet(String street) {
	this.street = street;
}

public String getPhone_num() {
	return phone_num;
}
public void setPhone_num(String phone_num) {
	this.phone_num = phone_num;
}
public String getProfile_photo() {
	return profile_photo;
}
public void setProfile_photo(String profile_photo) {
	this.profile_photo = profile_photo;
}
public float getFaith_value() {
	return faith_value;
}
public void setFaith_value(float faith_value) {
	this.faith_value = faith_value;
}
@Override
public String toString() {
	return "User [user_id=" + user_id + ", name=" + name + ", gender=" + gender + ", password=" + password + ", email="
			+ email + ", phone_num=" + phone_num  + ", profile_photo=" + profile_photo
			+ ", birthday=" + birthday + ", province=" + province + ", city=" + city + ", district=" + district
			+ ", street=" + street + ", faith_value=" + faith_value + "]";
}

}

