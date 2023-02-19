package ron.cyberstar.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Uni;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Subscribe extends PanacheEntityBase {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  public long id;

  public Subscribe(long cyberStarId, long subscribeId) {
    this.cyberStarId = cyberStarId;
    this.subscribeId = subscribeId;
  }

  public long cyberStarId;
  public long subscribeId;

  public Subscribe() {

  }

  public static Uni<Void> addFollowing(long cyberStarId, long followingId)  {
   return persist(new Subscribe(cyberStarId, followingId));
  }
}
