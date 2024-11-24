package org.example.commerce_site.application.partner;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.example.commerce_site.application.partner.dto.PartnerRequestDto;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Partner;
import org.example.commerce_site.infrastructure.partner.PartnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PartnerServiceTest {
	@InjectMocks
	private PartnerService partnerService;

	@Mock
	private PartnerRepository partnerRepository;

	@Test
	void create_ShouldSaveAndReturnPartner() {
		PartnerRequestDto.CreateWebHook dto = PartnerRequestDto.CreateWebHook.builder()
			.name("partner name")
			.bizId("test biz id")
			.build();

		Partner partner = PartnerRequestDto.CreateWebHook.toEntity(dto);

		when(partnerRepository.save(any(Partner.class))).thenReturn(partner);

		Partner result = partnerService.create(dto);

		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo("partner name");
		assertThat(result.getBusinessNumber()).isEqualTo("test biz id");

		verify(partnerRepository).save(any(Partner.class));
	}

	@Test
	void getPartnerById_ShouldReturnPartner_WhenPartnerExists() {
		Long partnerId = 1L;
		Partner partner = Partner.builder()
			.id(partnerId)
			.name("partner name")
			.businessNumber("test biz id")
			.build();

		when(partnerRepository.findById(partnerId)).thenReturn(Optional.ofNullable(partner));

		Partner result = partnerService.getPartner(partnerId);

		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(partnerId);
		assertThat(result.getName()).isEqualTo("partner name");
		assertThat(result.getBusinessNumber()).isEqualTo("test biz id");

		verify(partnerRepository).findById(partnerId);
	}

	@Test
	void getPartnerById_ShouldThrowCustomException_WhenPartnerDoesNotExist() {
		Long partnerId = 1L;

		when(partnerRepository.findById(partnerId)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> partnerService.getPartner(partnerId))
			.isInstanceOf(CustomException.class)
			.hasFieldOrPropertyWithValue("errorCode", ErrorCode.PARTNER_NOT_FOUND);

		verify(partnerRepository).findById(partnerId);
	}

	@Test
	void getPartnerByAuthId_ShouldReturnPartner_WhenPartnerExists() {
		String authId = "auth123";
		Partner partner = Partner.builder()
			.id(1L)
			.name("partner name")
			.businessNumber("test biz id")
			.authId(authId)
			.build();

		when(partnerRepository.findByAuthId(authId)).thenReturn(Optional.of(partner));

		Partner result = partnerService.getPartner(authId);

		assertThat(result).isNotNull();
		assertThat(result.getAuthId()).isEqualTo(authId);
		assertThat(result.getName()).isEqualTo("partner name");

		verify(partnerRepository).findByAuthId(authId);
	}

	@Test
	void getPartnerByAuthId_ShouldThrowCustomException_WhenPartnerDoesNotExist() {
		String authId = "auth123";

		when(partnerRepository.findByAuthId(authId)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> partnerService.getPartner(authId))
			.isInstanceOf(CustomException.class)
			.hasFieldOrPropertyWithValue("errorCode", ErrorCode.PARTNER_NOT_FOUND);

		verify(partnerRepository).findByAuthId(authId);
	}
}