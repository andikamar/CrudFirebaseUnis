package org.dika.crudfirebaseunis.ui.penduduk;

public class penduduk {
	public String nik;
	public String nama;
	public String alamat;
	public String hp;

	public penduduk(String nik, String nama, String alamat, String hp) {
		this.nik = nik;
		this.nama = nama;
		this.alamat = alamat;
		this.hp = hp;
	}

	public String getNik() {
		return nik;
	}

	public void setNik(String nik) {
		this.nik = nik;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public String getHp() {
		return hp;
	}

	public void setHp(String hp) {
		this.hp = hp;
	}
}
