package edu.rutgers.yygh.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.rutgers.yygh.common.exception.YyghException;
import edu.rutgers.yygh.common.result.Result;
import edu.rutgers.yygh.common.utils.MD5;
import edu.rutgers.yygh.hosp.service.HospitalSetService;
import edu.rutgers.yygh.model.hosp.HospitalSet;
import edu.rutgers.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;
import java.util.Random;

/**
 * @author: Shouzhi Fang(frank)
 * @create: 2022-01-15 22:52
 * @description:
 */
@Api(tags = "医院设备管理")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {

	@Autowired
	private HospitalSetService hospitalSetService;


	//1 查询医院设置表所有信息
	@ApiOperation(value = "获取所有医院设置")
	@GetMapping("/findAll")
	public Result findAll(){
		List<HospitalSet> list = hospitalSetService.list();
		return Result.ok(list);
	}


	//2 删除医院设置
	@ApiOperation(value = "逻辑删除医院设置")
	@DeleteMapping("{id}")
	public Result deleteHospSet(@PathVariable("id") Long id){
		boolean flag = hospitalSetService.removeById(id);
		if(flag){
			return Result.ok();
		}else{
			return Result.fail();
		}
	}

	// 条件查询带分页
	@PostMapping("/findPage/{current}/{limit}")
	public Result findPageHospSet(@PathVariable("current") Integer current,
								  @PathVariable("limit") Integer limit,
								  @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo){
		Page<HospitalSet> page = new Page<>(current, limit);
		//构造条件
		QueryWrapper<HospitalSet> hospitalSetQueryWrapper = new QueryWrapper<>();
		String hosname = hospitalSetQueryVo.getHosname(), hoscode=hospitalSetQueryVo.getHoscode();
		if(StringUtils.hasLength(hosname)){
			hospitalSetQueryWrapper.like("hosname", hospitalSetQueryVo.getHosname());
		}
		if(StringUtils.hasLength(hoscode)){
			hospitalSetQueryWrapper.eq("hoscode", hospitalSetQueryVo.getHoscode());
		}

		//调用方法实现分页查询
		Page<HospitalSet> page1 = hospitalSetService.page(page, hospitalSetQueryWrapper);
		return Result.ok(page1);
	}


	// 添加医院设置
	@PostMapping("/saveHospitalSet")
	public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet){
		//设置status 1可用 0不可用
		hospitalSet.setStatus(1);
		//签名密钥
		Random random = new Random();
		hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));

		//调用service添加
		boolean save = hospitalSetService.save(hospitalSet);

		if (save){
			return Result.ok();
		}else{
			return Result.fail();
		}
	}

	//根据id获取医院设置
	@GetMapping("getHospSet/{id}")
	public Result getHospitalSetById(@PathVariable("id") Long id){

		try{
			int a = 1/0;
		}catch (Exception e){
			throw new YyghException("arithmetic error", 201);
		}
		HospitalSet hospitalSet = hospitalSetService.getById(id);
		return Result.ok(hospitalSet);
	}

	//修改医院设置
	@PutMapping("updateHospSet")
	public Result updateHospSet(@RequestBody HospitalSet hospitalSet){
		boolean res = hospitalSetService.updateById(hospitalSet);
		if (res){
			return Result.ok();
		}else{
			return Result.fail();
		}
	}

	//批量删除医院设置
	@DeleteMapping("batchDelete")
	public Result batchDelete(@RequestBody List<Long> ids){
		boolean res = hospitalSetService.removeByIds(ids);
		if (res){
			return Result.ok();
		}else{
			return Result.fail();
		}
	}

	//医院设置锁定和解锁
	@PutMapping("lockHospitalSet/{id}/{status}")
	public Result lockHospitalSet(@PathVariable("id") Long id, @PathVariable("status") Integer status){
		HospitalSet hospitalSet = hospitalSetService.getById(id);
		hospitalSet.setStatus(status);
		boolean res = hospitalSetService.updateById(hospitalSet);
		if (res){
			return Result.ok();
		}else{
			return Result.fail();
		}
	}

	//发送签名密钥
	@PostMapping("sendSignKey/{id}")
	public Result lockHospitalSet(@PathVariable("id") Long id){
		HospitalSet hospitalSet = hospitalSetService.getById(id);
		String signKey = hospitalSet.getSignKey();
		String hoscode = hospitalSet.getHoscode();
		// TODO 发送短信
		return Result.ok();
	}
}
