package vodagone.data;

import vodagone.domain.Abonnement;

import java.util.ArrayList;

public interface IAbonnementMapper {

	void create (Abonnement abonnement);

	public Abonnement read (int id);

	public ArrayList<Abonnement> readAll ();

	public ArrayList <Abonnement> readFilter (String filter);

	public void update ();

	public void delete ();

}
