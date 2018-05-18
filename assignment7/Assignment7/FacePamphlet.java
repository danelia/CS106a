/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class FacePamphlet extends Program implements FacePamphletConstants {
	
	//i didn't write comments in FacePamphletDatabase and FacePamphletProfile
	//because everything was already written.

	/* instance variables */
	private JTextField changeStatus;
	private JTextField changePicture;
	private JTextField addFriend;
	private JTextField search;

	//canvas.
	private FacePamphletCanvas canvas;

	//profile that we see on screen.
	private FacePamphletProfile profileThatIsShown = null;

	//database.
	private FacePamphletDatabase database = new FacePamphletDatabase();

	//entered text in name text field.
	private String name;

	/**
	 * This method has the responsibility for initializing the interactors in
	 * the application, and taking care of any other initialization that needs
	 * to be performed.
	 */
	public void init() {
		changeStatus = new JTextField(TEXT_FIELD_SIZE);
		changePicture = new JTextField(TEXT_FIELD_SIZE);
		addFriend = new JTextField(TEXT_FIELD_SIZE);
		search = new JTextField(TEXT_FIELD_SIZE);

		add(changeStatus, WEST);
		add(new JButton("Change Status"), WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		add(changePicture, WEST);
		add(new JButton("Change Picture"), WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);

		add(addFriend, WEST);
		add(new JButton("Add Friend"), WEST);

		add(new JLabel("Name"), NORTH);
		add(search, NORTH);
		add(new JButton("Add"), NORTH);
		add(new JButton("Delete"), NORTH);
		add(new JButton("Lookup"), NORTH);

		canvas = new FacePamphletCanvas();
		add(canvas);

		addActionListeners();
		changeStatus.addActionListener(this);
		changePicture.addActionListener(this);
		addFriend.addActionListener(this);

	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		//what is entered in the top search text field.
		name = search.getText();
		//if add button is pressed.
		if (cmd.equals("Add") && !name.equals("")) {
			addProfile();
		//id delete button is pressed.
		} else if (cmd.equals("Delete") && !name.equals("")) {
			deleteProfile();
		//if lookup button is pressed.
		} else if (cmd.equals("Lookup") && !name.equals("")) {
			lookupForProfile();
		//if change status button or enter is pressed.
		} else if ((cmd.equals("Change Status") || e.getSource() == changeStatus) && !changeStatus.getText().equals("")) {
			changeProfileStatus();
			changeStatus.setText("");
		//if change picture button or enter is pressed.
		} else if ((cmd.equals("Change Picture") || e.getSource() == changePicture) && !changePicture.getText().equals("")) {
			changeProfilePicture();
			changePicture.setText("");
		//if add friend button or enter is pressed.
		} else if ((cmd.equals("Add Friend") || e.getSource() == addFriend) && !addFriend.getText().equals("")) {
			addFacePamphletFriend();
			addFriend.setText("");
		}
	}

	//adds new profile.
	private void addProfile() {
		if (!database.containsProfile(name)) {
			FacePamphletProfile profile = new FacePamphletProfile(name);
			database.addProfile(profile);
			canvas.displayProfile(profile);
			canvas.showMessage("New profile created");
			profileThatIsShown = profile;
		//if profile with entered name already exists it will show up a message that such profile exists.
		} else {
			FacePamphletProfile profile = database.getProfile(name);
			canvas.displayProfile(profile);
			canvas.showMessage("A profile with name " + name
					+ " already exsists.");
			profileThatIsShown = profile;
		}
	}

	//deletes profile.
	private void deleteProfile() {
		//if profile with entered name does not exist it will show up corresponding message.
		if (!database.containsProfile(name)) {
			canvas.showMessage("A profile with name " + name
					+ " does not exist.");
		//other case this profile will be deleted.
		} else {
			FacePamphletProfile profile = database.getProfile(name);
			if (profileThatIsShown == profile) {
				canvas.removeAll();
			}
			//if someone has this profile in friends this name will be removed from their friend list.
			Iterator<String> itt = profile.getFriends();
			while(itt.hasNext()){
				String str = itt.next();
				database.getProfile(str).removeFriend(
						profile.getName());
				if(profileThatIsShown!=profile){
					canvas.displayProfile(profileThatIsShown);
				}
			}
			database.deleteProfile(name);
			canvas.showMessage("Profile of " + name + " deleted.");
		}
	}

	//looks up for entered profile.
	private void lookupForProfile() {
		if (!database.containsProfile(name)) {
			canvas.showMessage("A profile with name " + name
					+ " does not exist.");
		} else {
			FacePamphletProfile profile = database.getProfile(name);
			canvas.displayProfile(profile);
			canvas.showMessage("Displaying " + name);
			profileThatIsShown = profile;
		}
	}

	//changes profile status.
	private void changeProfileStatus() {
		String status = changeStatus.getText();
		if (profileThatIsShown == null) {
			canvas.showMessage("Please select a profile to change status");
		} else {
			FacePamphletProfile profile = database
					.getProfile(profileThatIsShown.getName());
			profile.setStatus(profileThatIsShown.getName() + " is " + status);
			canvas.displayProfile(profile);
			canvas.showMessage("Status updated to " + status);
		}
	}

	//changes profile picture.
	private void changeProfilePicture() {
		if (profileThatIsShown == null) {
			canvas.showMessage("Please Select a profile to change picture");
			return;
		}
		GImage image = null;
		//attempt to open image file.
		try {
			image = new GImage("images/" + changePicture.getText());
		} catch (ErrorException ex) {
			canvas.showMessage("Unable to open image file: "
					+ changePicture.getText());
			return;
		}
		profileThatIsShown.setImage(image);
		canvas.displayProfile(profileThatIsShown);
		canvas.showMessage("Picture updated");
	}

	//adds friend.
	private void addFacePamphletFriend() {
		if (profileThatIsShown == null) {
			canvas.showMessage("Please select a profile to add friend");
			return;
		}
		if (profileThatIsShown.getName().equals(addFriend.getText())) {
			canvas.showMessage("You can't add yourself! Loser.");
			return;
		}
		if (!database.containsProfile(addFriend.getText())) {
			canvas.showMessage(addFriend.getText() + " does not exist");
			return;
			//cycle through existing friends to avoid duplicates.
		} else {
			Iterator<String> it = profileThatIsShown.getFriends();
			if (it != null) {
				while (it.hasNext()) {
					String friend = it.next();
					if (friend.equals(addFriend.getText())) {
						canvas.showMessage(profileThatIsShown.getName()
								+ " already has " + friend + " as a friend");
						return;
					}
				}
			}
		}
		// add friend to current profile.
		profileThatIsShown.addFriend(addFriend.getText());
		// add current profile to friend's list.
		database.getProfile(addFriend.getText()).addFriend(
				profileThatIsShown.getName());
		canvas.displayProfile(profileThatIsShown);
		canvas.showMessage(addFriend.getText() + " added as a friend");
	}
	
}