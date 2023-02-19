package ron.cyberstar.dto;

import io.quarkus.hibernate.reactive.panache.common.ProjectedFieldName;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class CyberStarDto {


  public CyberStarDto(String loginId, String name,
      @ProjectedFieldName("relationship.followerCount") Long followerCount,
      @ProjectedFieldName("relationship.followingCount") Long followingCount,
      @ProjectedFieldName("relationship.friendCount") Long friendCount) {
    this.loginId = loginId;
    this.name = name;
    this.followerCount = followerCount;
    this.followingCount = followingCount;
    this.friendCount = friendCount;
  }

  public String loginId;
  public String name;
  public Long followerCount;
  public Long followingCount;
  public Long friendCount;
}
