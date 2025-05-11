package com.example.actualtravellerkiviprojectui.dto.Event;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventDTO implements Parcelable {
    public Integer id;
    public EventType eventType;
    public Status status;
    public LocalDate created;
    public LocalDate startDate;
    public LocalDate endDate;
    public Set<Integer> ratingIds = new HashSet<>();
    public List<EventLocationDTO> locations = new ArrayList<>();
    public List<Integer> userIds = new ArrayList<>();
    public Integer ownerId;
    public String name;
    public String details;
    public List<Integer> commentIds = new ArrayList<>();
    public Integer skeletonId;

    public Integer imageId;
    public String language;
    public String rating;

    public enum EventType {
        TOUR, MEETUP
    }

    public enum Status {
        SCHEDULED, FINISHED, CANCELLED,
    }

    public EventDTO() {

    }

    public static final Creator<EventDTO> CREATOR = new Creator<EventDTO>() {
        @Override
        public EventDTO createFromParcel(Parcel in) {
            return new EventDTO(in);
        }

        @Override
        public EventDTO[] newArray(int size) {
            return new EventDTO[size];
        }
    };

    protected EventDTO(Parcel in) {
        if (in.readByte() == 0) { id = null; } else { id = in.readInt(); }
        if (in.readByte() == 0) { ownerId = null; } else { ownerId = in.readInt(); }
        name = in.readString();
        details = in.readString();
        if (in.readByte() == 0) { skeletonId = null; } else { skeletonId = in.readInt(); }
        if (in.readByte() == 0) { imageId = null; } else { imageId = in.readInt(); }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) { dest.writeByte((byte) 0); } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        if (ownerId == null) { dest.writeByte((byte) 0); } else {
            dest.writeByte((byte) 1);
            dest.writeInt(ownerId);
        }
        dest.writeString(name);
        dest.writeString(details);
        if (skeletonId == null) { dest.writeByte((byte) 0); } else {
            dest.writeByte((byte) 1);
            dest.writeInt(skeletonId);
        }
        if (imageId == null) { dest.writeByte((byte) 0); } else {
            dest.writeByte((byte) 1);
            dest.writeInt(imageId);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
