package test.service;

import vodagone.data.IAbonneeMapper;
import vodagone.domain.Abonnee;

import java.util.ArrayList;

public class AbonneeMapperMock implements IAbonneeMapper{

	private Abonnee getAbonnee () {
		return new Abonnee (1, "Sjoerd Scheffer", "sjoerd@b00n.org", "hahaha");
	}

	public AbonneeMapperMock () {
	};

	public void create () {};

	public Abonnee read (int id) {
		return getAbonnee ();
	}

	public Abonnee read (String email) {
		return getAbonnee ();
	}

	public ArrayList<Abonnee> readAll () {
		ArrayList<Abonnee> result = new ArrayList <Abonnee> ();
		result.add (getAbonnee ());
		return result;
	}

	public void update () {};

	public void delete () {};

}
