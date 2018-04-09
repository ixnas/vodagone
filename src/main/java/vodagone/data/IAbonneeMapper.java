package vodagone.data;

import vodagone.domain.Abonnee;

import java.util.ArrayList;

public interface IAbonneeMapper {

	void create ();

	Abonnee read (int id);

	Abonnee read (String email);

	ArrayList <Abonnee> readAll ();

	void update ();

	void delete ();
}
