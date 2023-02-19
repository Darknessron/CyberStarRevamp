package ron.cyberstar.service.impl;

import io.smallrye.mutiny.Uni;
import java.util.List;
import java.util.function.Predicate;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.LockModeType;
import javax.ws.rs.NotFoundException;
import ron.cyberstar.dto.CyberStarDto;
import ron.cyberstar.entity.CyberStar;
import ron.cyberstar.entity.Subscribe;
import ron.cyberstar.service.CyberStarService;
import ron.cyberstar.vo.PagedResult;

@ApplicationScoped
public class CyberStarServiceImpl implements CyberStarService {

  @Override
  public Uni<CyberStarDto> getCyberStar(String loginId) {
    return CyberStar.find("loginId", loginId).project(CyberStarDto.class).firstResult();
  }

  @Override
  public Uni<List<CyberStarDto>> getAllCyberStar() {
    return CyberStar.findAll().project(CyberStarDto.class).list();
  }

  @Override
  public Uni<CyberStarDto> subscribe(long currenUserId, String loginId) throws NotFoundException {

    Uni<CyberStar> star = CyberStar.find("loginId", loginId).withLock(LockModeType.PESSIMISTIC_READ)
        .firstResult();

    return star.onItem().ifNull().failWith((new NotFoundException("subscribe target not found")))
        .onItem().ifNotNull().invoke(c -> {
          //Subscribe target found
          Uni<CyberStar> currentUser = CyberStar.find("id", currenUserId).withLock(LockModeType.READ)
              .firstResult();
          currentUser.onItem().ifNull().failWith(new NotFoundException("current user not found"))
              //Current User found
          .onItem().ifNotNull().invoke(u -> {

                Subscribe.addFollowing(c.id, currenUserId)
                    .onItem().invoke(() -> {

                      //Update relationship counting
                      c.relationship.followingCount += 1;
                      u.relationship.followerCount += 1;

                      c.relationship.persistAndFlush();
                      u.relationship.persistAndFlush();


                    }).subscribe().with(item -> System.out.println("Line 52: " + item));

                //Check is friend or not
               CyberStar.isFriend(c.id, currenUserId).onItem().invoke(i -> {
                 if (i > 0) {
                   //Update relationship counting
                   c.relationship.friendCount += 1;
                   u.relationship.friendCount += 1;

                   c.relationship.persistAndFlush();
                   u.relationship.persistAndFlush();
                 }
               }).subscribe().with(item -> System.out.println("Line 66: " + item));

              }).subscribe().with(item -> System.out.println("Line 68: " + item));
        }).map(c -> new CyberStarDto(c.loginId, c.name, c.relationship.followerCount, c.relationship.followingCount, c.relationship.friendCount));


  }

  @Override
  public void unsubscribe(long currenUserId, String loginId) throws NotFoundException {

  }

  @Override
  public PagedResult getFollowers(String loginId, int index, int pageSize) {
    return null;
  }

  @Override
  public PagedResult getFollowings(String loginId, int index, int pageSize) {
    return null;
  }

  @Override
  public PagedResult getFriends(String loginId, int index, int pageSize) {
    return null;
  }
}
