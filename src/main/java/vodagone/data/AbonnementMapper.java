package vodagone.data;

import vodagone.domain.Abonnement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AbonnementMapper {

	private DataAccess dataAccess;
	private IdentityMapper identityMapper;

	public AbonnementMapper (DataAccess dataAccess, IdentityMapper identityMapper) {
		this.dataAccess = dataAccess;
		this.identityMapper = identityMapper;
	}

	public void create (Abonnement abonnement) {

	}

	public Abonnement read (int id) {
		if (!identityMapper.alreadyInIdentityMap (id)) {
			Abonnement abonnement = null;
			try {
				PreparedStatement st = dataAccess.getConnection ().prepareStatement ("SELECT * FROM Abonnement WHERE id = ?");
				st.setInt(1, id);
				ResultSet rs = dataAccess.query (st);
				while (rs.next ())
					abonnement = new Abonnement (
							rs.getInt ("id"),
							rs.getString ("aanbieder"),
							rs.getString ("naam"),
							rs.getFloat ("prijsPerMaand"),
							rs.getBoolean ("deelbaar"),
							rs.getBoolean ("verdubbeling")
					);
			} catch (SQLException e) {
				System.out.println ("Fokt op");
			}
			identityMapper.addToIdentityMap (abonnement);
			return abonnement;
		}
		return (Abonnement) identityMapper.getFromIdentityMap (id);
	}

	public ArrayList<Abonnement> readAll () {
		ArrayList<Abonnement> result = new ArrayList<Abonnement> ();
		identityMapper.clearIdentityMap ();
		try {
			ResultSet rs = dataAccess.query (dataAccess.getConnection ().prepareStatement ("SELECT * FROM Abonnement"));
			while (rs.next ())
				result.add (new Abonnement (
						rs.getInt ("id"),
						rs.getString ("aanbieder"),
						rs.getString ("naam"),
						rs.getFloat ("prijsPerMaand"),
						rs.getBoolean ("deelbaar"),
						rs.getBoolean ("verdubbeling")
				));
			for (int i = 0; i < result.size(); i++)
				identityMapper.addToIdentityMap (result.get(i));
		} catch (SQLException e) {
			System.out.println ("Fokt op");
		}
		return result;
	}

	public ArrayList<Abonnement> readFilter (String filter) {
		if (filter == null) return readAll ();
		ArrayList<Abonnement> result = new ArrayList<Abonnement> ();
		try {
			filter = filter
					.replace("!", "!!")
					.replace("%", "!%")
					.replace("_", "!_")
					.replace("[", "![");
			PreparedStatement st = dataAccess.getConnection ().prepareStatement ("SELECT * FROM Abonnement WHERE aanbieder LIKE ? OR naam LIKE ?");
			st.setString (1, "%" + filter + "%");
			st.setString (2, "%" + filter + "%");
			ResultSet rs = dataAccess.query (st);
			while (rs.next ())
				result.add (new Abonnement (
						rs.getInt ("id"),
						rs.getString ("aanbieder"),
						rs.getString ("naam"),
						rs.getFloat ("prijsPerMaand"),
						rs.getBoolean ("deelbaar"),
						rs.getBoolean ("verdubbeling")
				));
		} catch (SQLException e) {
			System.out.println ("Fokt op");
		}
		return result;
	}

	public void update () {
	}

	public void delete () {
	}

}
