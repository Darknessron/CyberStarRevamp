package ron.cyberstar.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import io.smallrye.mutiny.Uni;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQuery(name = "isFriend", query = "select count(s.id) from Subscribe s where s.subscribeId = :currentUserId and s.cyberStarId = :followingId")
public class CyberStar extends PanacheEntityBase {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  public long id;

  @Column(name = "login_id")
  public String loginId;
  public String name;

  @OneToOne
  @JoinColumn(name = "id", referencedColumnName = "id")
  public Relationship relationship;

  public static Uni<Long> isFriend(long currentUserId, long followingId)  {
    return count("#isFriend", Parameters.with("currentUserId", currentUserId).and("followingId", followingId));
  }
}
