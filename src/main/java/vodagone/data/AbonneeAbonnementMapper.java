package vodagone.data;

import vodagone.domain.Abonnee;
import vodagone.domain.AbonneeAbonnement;
import vodagone.domain.Abonnement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AbonneeAbonnementMapper {

	private DataAccess dataAccess;
	private AbonneeMapper abonneeMapper;
	private AbonnementMapper abonnementMapper;
	private IdentityMapper identityMapper;

	public AbonneeAbonnementMapper (DataAccess dataAccess, AbonneeMapper abonneeMapper, AbonnementMapper abonnementMapper, IdentityMapper identityMapper) {
		this.dataAccess = dataAccess;
		this.abonneeMapper = abonneeMapper;
		this.abonnementMapper = abonnementMapper;
		this.identityMapper = identityMapper;
	}

	public boolean create (int abonneeId, int abonnementId) {
		Abonnement abonnement = this.abonnementMapper.read(abonnementId);
		Abonnee abonnee = this.abonneeMapper.read(abonneeId);
		if (abonnement != null && abonnee != null) {
			try {
				java.util.Date date = new java.util.Date ();
				PreparedStatement st = dataAccess.getConnection ().prepareStatement ("INSERT INTO AbonneeAbonnement (`abonnee`, `abonnement`, `startDatum`, `status`, `verdubbeling`) VALUES (?, ?, ?, ?, ?)");
				st.setInt (1, abonneeId);
				st.setInt (2, abonnementId);
				st.setDate (3, new java.sql.Date (date.getTime ()));
				st.setString (4, "proef");
				st.setString (5, "standaard");
				st.execute ();
				return true;
			} catch (SQLException e) {
				e.printStackTrace ();
				return false;
			}
		}
		return false;
	}

	public ArrayList <AbonneeAbonnement> readAllFromAbonnee (int abonneeId) {
		Abonnee abonnee = abonneeMapper.read (abonneeId);
		ArrayList <AbonneeAbonnement> result = new ArrayList <AbonneeAbonnement> ();
		if (abonnee != null) {
			try {
				PreparedStatement st = dataAccess.getConnection ().prepareStatement ("SELECT * FROM AbonneeAbonnement WHERE abonnee = ?");
				st.setInt (1, abonneeId);
				ResultSet rs = st.executeQuery ();
				while (rs.next ())
					result.add (
							new AbonneeAbonnement (
									rs.getInt ("id"),
									abonnee,
									abonnementMapper.read (rs.getInt ("abonnement")),
									rs.getDate ("startDatum"),
									rs.getString ("status"),
									rs.getString ("verdubbeling")
							)
					);
				return result;
			} catch (SQLException e) {
				System.out.println ("Fokt op");
			}
		}
		return null;
	}

	public AbonneeAbonnement read (int abonneeAbonnementId) {
		try {
			PreparedStatement st = dataAccess.getConnection ().prepareStatement ("SELECT * FROM AbonneeAbonnement WHERE id = ?");
			st.setInt (1, abonneeAbonnementId);
			ResultSet rs = st.executeQuery ();
			while (rs.next ())
				return new AbonneeAbonnement (
						rs.getInt ("id"),
						abonneeMapper.read (rs.getInt ("abonnee")),
						abonnementMapper.read (rs.getInt ("abonnement")),
						rs.getDate ("startDatum"),
						rs.getString ("status"),
						rs.getString ("verdubbeling")
				);
		} catch (SQLException e) {
			System.out.println ("Fokt op");
		}
		return null;
	}

	public boolean update () {
		return false;
	}

	public boolean delete (AbonneeAbonnement abonneeAbonnement) {
		if (abonneeAbonnement != null) {
			try {
				PreparedStatement st = dataAccess.getConnection ().prepareStatement ("UPDATE AbonneeAbonnement SET status = 'opgezegd' WHERE id = ?");
				st.setInt (1, abonneeAbonnement.getId ());
				st.executeUpdate ();
				abonneeAbonnement.setStatus ("opgezegd");
				identityMapper.addToIdentityMap (abonneeAbonnement);
				return true;
			} catch (SQLException e) {
				e.printStackTrace ();
				return false;
			}
		}
		return false;
	}
}
