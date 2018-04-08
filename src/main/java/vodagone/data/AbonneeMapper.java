package vodagone.data;

import vodagone.domain.Abonnee;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AbonneeMapper {

	private DataAccess dataAccess;
	private IdentityMapper identityMapper;

	public AbonneeMapper (DataAccess dataAccess, IdentityMapper identityMapper) {
		this.dataAccess = dataAccess;
		this.identityMapper = identityMapper;
	};

	public void create () {};

	public Abonnee read (int id) {
		if (!identityMapper.alreadyInIdentityMap (id)) {
			Abonnee abonnee = null;
			try {
				PreparedStatement st = dataAccess.getConnection ().prepareStatement ("SELECT * FROM Abonnee WHERE id = ?");
				st.setInt(1, id);
				ResultSet rs = dataAccess.query (st);
				while (rs.next ())
					abonnee = new Abonnee (
							rs.getInt ("id"),
							rs.getString ("naam"),
							rs.getString ("email"),
							rs.getString ("wachtwoord")
					);
			} catch (SQLException e) {
				System.out.println ("Fokt op");
			}
			identityMapper.addToIdentityMap (abonnee);
			return abonnee;
		}
		return (Abonnee) identityMapper.getFromIdentityMap (id);
	}

	public Abonnee read (String email) {
		if (!identityMapper.alreadyInIdentityMap (email)) {
			Abonnee abonnee = null;
			try {
				PreparedStatement st = dataAccess.getConnection ().prepareStatement ("SELECT * FROM Abonnee WHERE email = ?");
				st.setString(1, email);
				ResultSet rs = dataAccess.query (st);
				while (rs.next ())
					abonnee = new Abonnee (
							rs.getInt ("id"),
							rs.getString ("naam"),
							rs.getString ("email"),
							rs.getString ("wachtwoord")
					);
			} catch (SQLException e) {
				System.out.println ("Fokt op");
			}
			identityMapper.addToIdentityMap (abonnee);
			return abonnee;
		}
		return (Abonnee) identityMapper.getFromIdentityMap (email);
	}

	public ArrayList<Abonnee> readAll () {
		ArrayList<Abonnee> result = new ArrayList<Abonnee> ();
		identityMapper.clearIdentityMap ();
		try {
			ResultSet rs = dataAccess.query (dataAccess.getConnection ().prepareStatement ("SELECT * FROM Abonnee"));
			while (rs.next ())
				result.add (new Abonnee (
						rs.getInt ("id"),
						rs.getString ("naam"),
						rs.getString ("email"),
						rs.getString ("wachtwoord")
				));
			for (int i = 0; i < result.size(); i++)
				identityMapper.addToIdentityMap (result.get(i));
		} catch (SQLException e) {
			System.out.println ("Fokt op");
		}
		return result;
	}

	public void update () {};

	public void delete () {};

}
