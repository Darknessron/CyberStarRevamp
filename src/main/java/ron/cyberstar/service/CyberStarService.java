package ron.cyberstar.service;

import io.smallrye.mutiny.Uni;
import java.util.List;
import javax.ws.rs.NotFoundException;
import ron.cyberstar.dto.CyberStarDto;
import ron.cyberstar.entity.CyberStar;
import ron.cyberstar.vo.PagedResult;

public interface CyberStarService {
  Uni<CyberStarDto> getCyberStar(String loginId);

  Uni<List<CyberStarDto>> getAllCyberStar();

  Uni<CyberStarDto> subscribe(long currenUserId, String loginId) throws NotFoundException;
  void unsubscribe(long currenUserId, String loginId) throws NotFoundException;
  PagedResult getFollowers(String loginId, int index, int pageSize);

  PagedResult getFollowings(String loginId, int index, int pageSize);

  PagedResult getFriends(String loginId, int index, int pageSize);
}
