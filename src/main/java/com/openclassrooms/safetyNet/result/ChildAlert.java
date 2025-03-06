package com.openclassrooms.safetyNet.result;

import java.util.List;

public class ChildAlert {
    List<ChildInformation> childInformations;
    List<FamilyMember> familyMembers;

    public ChildAlert(List<ChildInformation> childInformations, List<FamilyMember> familyMembers) {
        this.childInformations = childInformations;
        this.familyMembers = familyMembers;
    }

    public List<ChildInformation> getChildInformations() {
        return childInformations;
    }

    public List<FamilyMember> getFamilyMembers() {
        return familyMembers;
    }
}
