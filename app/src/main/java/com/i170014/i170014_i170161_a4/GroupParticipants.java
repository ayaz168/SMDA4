package com.i170014.i170014_i170161_a4;

public class GroupParticipants {
    String Id,UserId,GroupId;

    public GroupParticipants(String id, String userId, String groupId) {
        Id = id;
        UserId = userId;
        GroupId = groupId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String groupId) {
        GroupId = groupId;
    }
}
