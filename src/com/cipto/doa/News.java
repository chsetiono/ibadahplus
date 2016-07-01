package com.cipto.doa;

public class News {

	 private String judul;
	 private String imageUrl;
	 private String id;
	 private String tanggal;
	 private String isi;
	 private String sumber;
	 public String getJudul() {
	  return judul;
	 }
	 public void setJudul(String judul) {
	  this.judul = judul;
	 }
	 public String getIsi(){
		 return isi;
	 }
	 public void setIsi(String isi){
		 
		 this.isi=isi;
	 }
	 public String getImageUrl() {
	  return imageUrl;
	 }
	 public void setImageUrl(String imageUrl) {
	  this.imageUrl = imageUrl;
	 }
	 public String getId() {
	  return id;
	 }
	 public void setId(String id) {
	  this.id = id;
	 }
	 
	 public String getTanggal(){
		 return tanggal;
	 }
	 
	 public void setTanggal(String tanggal){
		 this.tanggal=tanggal;
	 }
	 
	 public String getSumber(){
		 return sumber;
	 }
	 
	 public void setSumber(String sumber){
		 this.sumber=sumber;
	 }
	}