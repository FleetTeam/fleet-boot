package org.fleet.modules.system.service;

import org.fleet.modules.system.entity.SysDictItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @Author fleet-team
 * @since 2021-04-19
 */
public interface ISysDictItemService extends IService<SysDictItem> {
    public List<SysDictItem> selectItemsByMainId(String mainId);
}
