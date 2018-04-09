package vodagone.data;

import vodagone.domain.AbonneeAbonnement;

import java.util.ArrayList;

public interface IAbonneeAbonnementMapper {

	boolean create (int abonneeId, int abonnementId);

	ArrayList <AbonneeAbonnement> readAllFromAbonnee (int abonneeId);

	AbonneeAbonnement read (int abonneeAbonnementId);

	boolean update (AbonneeAbonnement abonneeAbonnement);

	boolean delete (AbonneeAbonnement abonneeAbonnement);
}
