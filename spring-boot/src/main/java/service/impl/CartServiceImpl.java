package service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dao.CartDao;
import domain.Cart;
import service.CartService;

/**
 * 
 * service.impl CartServiceImpl.java
 * @author 张鹏飞
 * @time 2017年5月13日 下午4:08:09
 *
 */
@Service
public class CartServiceImpl implements CartService{
	@Autowired
	private CartDao CartDao;
	@Override
	public Cart getById(Long id) {
		  return CartDao.findOne(id);
	}
	@Override
	public Page<Cart> FindList(Pageable pageable) {
		return CartDao.findAll(pageable);
	}
//通过商品和用户找不到之后才能继续加入购物车
	@Override
	public void save(Cart Cart) {
		if(!this.findByGoodsAndUser(Cart)){
			CartDao.save(Cart);
		}
	}
	@Override
	public void delete(Long id) {
		CartDao.delete(id);
		
	}

	@Override
	public Cart update(Cart Cart) {
		return CartDao.save(Cart);
	}

	@Override
	public Boolean findByGoodsAndUser(Cart Cart) {
		List<Cart> carts =CartDao.findByGoodsAndUser(Cart.getGoods(),Cart.getUser());
		if((carts!=null)&&(!carts.isEmpty())&&carts.size()>0){
			return true;
		}else{
			return false;
		}
	}
    
	@Override
	public List<Cart> FindMyList(Pageable pageable,Cart Cart) {
		return CartDao.findByUser(Cart.getUser());
	}

}
