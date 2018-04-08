package vodagone.data;

import vodagone.domain.MappableObject;

import java.util.ArrayList;

public class IdentityMapper {

	private ArrayList<MappableObject> objects;

	public IdentityMapper () {
		objects = new ArrayList<MappableObject>();
	}

	public boolean alreadyInIdentityMap (int id) {
		for (MappableObject object : objects)
			if (object.getId () == id) {
				return true;
			}
		return false;
	}

	public boolean alreadyInIdentityMap (String email) {
		for (MappableObject object : objects)
			if (object.getEmail () == email) {
				return true;
			}
		return false;
	}

	public void clearIdentityMap () {
		objects = new ArrayList<MappableObject>();
	}

	public void addToIdentityMap (MappableObject object) {
		for (int i = 0; i < objects.size (); i++)
			if (objects.get (i).getId() == object.getId())
				objects.remove (i);
		objects.add(object);
	}

	public MappableObject getFromIdentityMap (int id) {
		for (MappableObject object : objects)
			if (object.getId () == id)
				return object;
		return null;
	}

	public MappableObject getFromIdentityMap (String email) {
		for (MappableObject object : objects)
			if (object.getEmail () == email)
				return object;
		return null;
	}

}
