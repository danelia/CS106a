/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import acm.graphics.*;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.util.*;

public class FacePamphletCanvas extends GCanvas implements
		FacePamphletConstants {

	/* instance variables */
	private GLabel message;
	private GLabel name;
	private GImage image;

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public FacePamphletCanvas() {
		// You fill this in
	}

	/**
	 * This method displays a message string near the bottom of the canvas.
	 * Every time this method is called, the previously displayed message (if
	 * any) is replaced by the new message text passed in.
	 */
	public void showMessage(String msg) {
		if (message != null) {
			remove(message);
		}
		message = new GLabel(msg);
		message.setLocation((getWidth() / 2) - (message.getWidth() / 2),
				getHeight() - BOTTOM_MESSAGE_MARGIN);
		message.setFont(MESSAGE_FONT);
		add(message);
	}

	/**
	 * This method displays the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages displayed near the
	 * bottom of the screen) and then the given profile is displayed. The
	 * profile display includes the name of the user from the profile, the
	 * corresponding image (or an indication that an image does not exist), the
	 * status of the user, and a list of the user's friends in the social
	 * network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		if (profile == null) {
			return;
		}
		addName(profile);
		addImage(profile);
		addStatus(profile);
		addFriends(profile);
	}

	// adds friend list on canvas.
	private void addFriends(FacePamphletProfile profile) {
		GLabel profileFriends = new GLabel("Friends:");
		profileFriends.setLocation(getWidth() / 2, name.getY() + IMAGE_MARGIN);
		profileFriends.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(profileFriends);
		// vertical spacing.
		double margin = 0;

		// cycle through list of friends.
		Iterator<String> it = profile.getFriends();
		if (it != null) {
			while (it.hasNext()) {
				String friend = it.next();
				GLabel friendLabel = new GLabel(friend);
				friendLabel.setFont(PROFILE_FRIEND_FONT);
				double height = friendLabel.getHeight();
				friendLabel.setLocation(getWidth() / 2, profileFriends.getY()
						+ height + margin);
				margin += height;
				add(friendLabel);
			}
		}
	}

	// adds status on canvas.
	private void addStatus(FacePamphletProfile profile) {
		GLabel statusLabel = new GLabel("");
		String statusStr = profile.getStatus();
		if (statusStr == null) {
			statusLabel.setLabel("No current status");
		} else {
			statusLabel.setLabel(statusStr);
		}
		statusLabel.setFont(PROFILE_STATUS_FONT);
		double yStatus = name.getY() + IMAGE_MARGIN + IMAGE_HEIGHT
				+ STATUS_MARGIN;
		add(statusLabel, LEFT_MARGIN, yStatus);
	}

	// adds image on canvas.
	private void addImage(FacePamphletProfile profile) {
		// if there is no picture on the picture's place will be drawn rectangle
		// with "No Image" message in it.
		if (profile.getImage() == null) {
			GRect emptyImgRect = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
			emptyImgRect.setLocation(LEFT_MARGIN, name.getY() + IMAGE_MARGIN);
			add(emptyImgRect);
			GLabel emptyImgLabel = new GLabel("No Image");
			emptyImgLabel.setFont(PROFILE_IMAGE_FONT);
			double imgLabelLength = emptyImgLabel.getWidth();

			double imgLabelMiddle = (emptyImgLabel.getAscent() - emptyImgLabel
					.getDescent()) / 2;

			emptyImgLabel.setLocation(LEFT_MARGIN
					+ (emptyImgRect.getWidth() / 2) - (imgLabelLength / 2),
					emptyImgRect.getY() + (emptyImgRect.getHeight() / 2)
							+ imgLabelMiddle);

			add(emptyImgLabel);
		//in other case it will add picture.
		} else {
			image = profile.getImage();
			image.setBounds(LEFT_MARGIN, name.getY() + IMAGE_MARGIN,
					IMAGE_WIDTH, IMAGE_HEIGHT);
			add(image);
		}
	}

	//adds name on canvas at the top of picture.
	private void addName(FacePamphletProfile profile) {
		name = new GLabel(profile.getName());
		name.setFont(PROFILE_NAME_FONT);
		double nameHeight = name.getAscent();
		name.setLocation(LEFT_MARGIN, TOP_MARGIN + nameHeight);
		name.setColor(Color.blue);
		add(name);
	}
}