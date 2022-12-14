package com.ssafy.home.estate.service;

import java.util.*;

import com.ssafy.home.estate.dto.*;
import com.ssafy.home.estate.entity.*;
import com.ssafy.home.estate.mapper.EstateMapper;
import com.ssafy.home.estate.repository.BusStationRepository;
import com.ssafy.home.estate.repository.DongCodeRepository;
import com.ssafy.home.estate.repository.SubwayStationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EstateServiceImpl implements EstateService {

	private final EstateMapper estateMapper;
	private final DongCodeRepository dongCodeRepository;
	private final SubwayStationRepository subwayStationRepository;
	private final BusStationRepository busStationRepository;

	@Autowired
	public EstateServiceImpl(EstateMapper estateMapper, DongCodeRepository dongCodeRepository, SubwayStationRepository subwayStationRepository, BusStationRepository busStationRepository) {
		this.estateMapper = estateMapper;
		this.dongCodeRepository = dongCodeRepository;
		this.subwayStationRepository = subwayStationRepository;
		this.busStationRepository = busStationRepository;
	}


	@Override
	public List<AptTradeInfoDto> getAptTradeListByOption(Map<Object, Object> option) throws Exception {
		return estateMapper.getAptTradeListByOption(option);
	}


	@Override
	public List<String> getSiList() throws Exception {
		return estateMapper.getSiList();
	}


	@Override
	public List<String> getGuGunList(String si) throws Exception {
		return estateMapper.getGuGunList(si);
	}


	@Override
	public List<String> getDongList(Map<String,String> map) throws Exception {
		return estateMapper.getDongList(map);
	}


	@Override
	public List<DongCode> getInterestLocation(String userId) throws Exception {
		return estateMapper.getInterestLocation(userId);
	}

	@Override
	public List<AptSimpleInfoDto> getAptListByOption(int dongCode) throws Exception {
		return estateMapper.getAptListByOption(dongCode);
	}

	@Override
	public List<SimpleBuildingDto> getBuildingListByKeyword(Map<String, Object> options) throws Exception {

		PriorityQueue<SimpleBuildingDto> pq = new PriorityQueue<>(new Comparator<SimpleBuildingDto>(){

			@Override
			public int compare(SimpleBuildingDto o1, SimpleBuildingDto o2) {
				return o1.getName().length() - o2.getName().length();
			}
		});

		int limit = 100;
		int resultSize = 20;
		options.put("limit", limit);

		// ?????? keyword??? ????????? subwayStation ?????? ????????????
		List<SubwayStation> sStations = estateMapper.getSubwayStationByKeywordAndLimit(options);
		log.info("sStations : " + sStations.size());
		for (SubwayStation s: sStations) {
			SimpleBuildingDto sbd = SimpleBuildingDto.builder()
					.pk(s.getId())
					.name(s.getName())
					.tableName("SubwayStation")
					.build();
			pq.offer(sbd);
		}

		// ?????? keyword??? ????????? busStation ?????? ????????????
		List<BusStation> bStations = estateMapper.getBusStationByKeywordAndLimit(options);
		log.info("bStations : " + bStations.size());
		for (BusStation b: bStations) {
			SimpleBuildingDto sbd = SimpleBuildingDto.builder()
					.pk(b.getId())
					.name(b.getName())
					.tableName("BusStation")
					.build();
			pq.offer(sbd);
		}

		// ?????? keyword??? ????????? business ?????? ????????????
		List<Business> businessList = estateMapper.getBusinessByKeywordAndLimit(options);
		log.info("businessList : " + businessList.size());
		for (Business b: businessList) {
			SimpleBuildingDto sbd = SimpleBuildingDto.builder()
					.pk(b.getId())
					.name(b.getName())
					.tableName("Business")
					.dongCode(b.getDongCode())
					.build();
			pq.offer(sbd);
		}

		// ?????? keyword??? ????????? apt ?????? ????????????
		List<HouseInfo> aptList = estateMapper.getAptByKeywordAndLimit(options);
		log.info("aptList : " + aptList.size());
		for (HouseInfo h: aptList) {
			SimpleBuildingDto sbd = SimpleBuildingDto.builder()
					.pk((long)h.getAptCode())
					.name(h.getApartmentName())
					.tableName("HouseInfo")
					.dongCode(h.getDongCode())
					.build();
			pq.offer(sbd);
		}

		// ?????? keyword??? ????????? real_estate ?????? ??????
		List<RealEstate> estateList = estateMapper.getEstateByKeywordAndLimit(options);
		log.info("estateList : " + estateList.size());
		for(RealEstate r : estateList) {
			SimpleBuildingDto sbd = SimpleBuildingDto.builder()
					.pk((long)r.getId())
					.name(r.getName())
					.tableName("RealEstate")
					.dongCode(r.getDongCode())
					.build();
			pq.offer(sbd);
		}

		List<SimpleBuildingDto> results = new ArrayList<>();

		Set<String> dongCodeList = new HashSet<>();

		// ????????? resultSize?????? ??????
		for(int i=0; i<resultSize; i++) {
			if(!pq.isEmpty()) {
				SimpleBuildingDto sbd = pq.poll();
				if(sbd.getDongCode() != null) {
					dongCodeList.add(sbd.getDongCode());
				}
				results.add(sbd);
			}
		}

		List<String> temp = new ArrayList<>(dongCodeList);
		// ?????? ????????????????????? ???????????? ???????????? ????????????
		List<DongCode> dongCodeResult = dongCodeRepository.findByDongCodeIn(temp);

		HashMap<String, DongCode> hashList = new HashMap<>();
		for (DongCode dongCode : dongCodeResult) {
			hashList.put(dongCode.getDongCode(), dongCode);
		}

		for (SimpleBuildingDto result : results) {
			if(result.getDongCode() == null)
				continue;
			DongCode dc = hashList.get(result.getDongCode());
			result.setSi(dc.getSidoName());
			result.setDong(dc.getDongName());
			result.setGugun(dc.getGugunName());
		}

		for (SimpleBuildingDto result : results) {
			System.out.println(result.toString());
		}


		return results;
	}

	@Override
	public void addInterestLocation(String userId, String dongCode) throws Exception {

		List<String> interestList = estateMapper.getInterestList(userId);

//		for(String s : interestList) {
//			System.out.print(s + " : ");
//		}
		if(interestList.contains(dongCode)) {
			throw new IllegalArgumentException("?????? ????????? dongCode?????????.");
		}

		Map<String, Object> options = new HashMap<>();
		options.put("userId", userId);
		options.put("dongCode", dongCode);

		estateMapper.addInterestLocation(options);
	}

	@Override
	public void deleteInterestLocation(String userId, String dongCode) throws Exception {
		Map<String, Object> options = new HashMap<>();
		options.put("userId", userId);
		options.put("dongCode", dongCode);

		estateMapper.deleteInterestLocation(options);
	}

	@Override
	public List<AptSimpleInfoDto> getAptListByLocation(Map<String,Object> options) throws Exception {

		options.put("tableName", ((String)options.get("tableName")).toLowerCase());
		List<AptSimpleInfoDto> aptList = estateMapper.getAptListByLocation(options);

		for(AptSimpleInfoDto sbd : aptList) {
//			log.info(String.valueOf(sbd.getDistance()));
			sbd.setDistance(sbd.getDistance()/1000);
		}

//		for(AptSimpleInfoDto sbd : aptList) {
//			System.out.println("distance : " + sbd.getDistance());
//		}
		return aptList;
	}

	@Override
	public HouseInfo getAptById(Long aptId) throws Exception {
		return estateMapper.getAptById(aptId);
	}

	@Override
	public List<HouseDeal> getTradeListByAptId(Long aptId) throws Exception {
		return estateMapper.getTradeListByAptId(aptId);
	}

	@Override
	public BuildingInfoAndAptList getAptNearBuilding(String tableName, Long pk, Long distance) throws Exception {

		SimpleBuildingDto simpleBuildingDto = new SimpleBuildingDto();
		Map<String, Object> location = new HashMap<>();
		location.put("distance",distance);
		// 1. ?????? ????????? ??????
		switch(tableName) {
			case "HouseInfo":
				HouseInfo houseInfo = estateMapper.getAptById(pk);
				location.put("lng", houseInfo.getLng());
				location.put("lat", houseInfo.getLat());
				simpleBuildingDto = SimpleBuildingDto.builder()
						.pk(houseInfo.getAptCode())
						.name(houseInfo.getApartmentName())
						.tableName("HouseInfo")
						.property("HouseInfo")
						.lat(houseInfo.getLat())
						.lng(houseInfo.getLng())
						.build();
				break;
			case "Business":
				Business business = estateMapper.getBusinessById(pk);
				location.put("lng", business.getLng());
				location.put("lat", business.getLat());
				String comCode = business.getSmallCommercialCode();

				CommercialCode cc = estateMapper.getCommercialCodeBySmallCode(comCode);
				simpleBuildingDto = SimpleBuildingDto.builder()
						.pk(business.getId())
						.name(business.getName())
						.tableName("Business")
						.lat(business.getLat())
						.lng(business.getLng())
						.property(cc.getBigName()+"-"+cc.getMediumName()+"-"+cc.getSmallName())
						.build();
				break;
			case "RealEstate":
				break;
			case "BusStation":
				int intPk = Long.valueOf(pk).intValue();
				BusStation bStation = busStationRepository.findById(intPk).get();
				location.put("lng", bStation.getLng());
				location.put("lat", bStation.getLat());
				simpleBuildingDto = SimpleBuildingDto.builder()
						.pk(bStation.getId())
						.name(bStation.getName())
						.tableName("BusStation")
						.property("BusStation")
						.build();
				break;

			case "SubwayStation":
				intPk = Long.valueOf(pk).intValue();
				SubwayStation sStation = subwayStationRepository.findById(intPk).get();
				location.put("lng", sStation.getLng());
				location.put("lat", sStation.getLat());
				simpleBuildingDto = SimpleBuildingDto.builder()
						.pk(sStation.getId())
						.name(sStation.getName())
						.tableName("SubwayStation")
						.property("SubwayStation")
						.build();
				break;

		}

		// 2. ?????? ????????? ?????? ??????
		List<AptSimpleInfoDto> aptList = estateMapper.getAptListByLocation(location);
		for(AptSimpleInfoDto asid : aptList) {
			asid.setDistance(asid.getDistance()/1000);
		}

		return new BuildingInfoAndAptList(simpleBuildingDto, aptList);
	}


}
