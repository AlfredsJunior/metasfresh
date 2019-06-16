package de.metas.dataentry.data;

import static de.metas.util.Check.assume;
import static de.metas.util.Check.fail;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import de.metas.dataentry.DataEntryFieldId;
import de.metas.dataentry.DataEntryListValueId;
import de.metas.dataentry.FieldType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@EqualsAndHashCode
public abstract class DataEntryRecordField<T>
{
	@Getter
	@JsonIgnore
	private final DataEntryFieldId dataEntryFieldId;

	@Getter
	@JsonIgnore
	private final CreatedUpdatedInfo createdUpdatedInfo;

	protected DataEntryRecordField(
			@NonNull final DataEntryFieldId dataEntryFieldId,
			@NonNull final CreatedUpdatedInfo createdUpdatedInfo)
	{
		this.dataEntryFieldId = dataEntryFieldId;
		this.createdUpdatedInfo = createdUpdatedInfo;
	}

	public abstract T getValue();

	@SuppressWarnings("unchecked")
	public static <T> DataEntryRecordField<T> createDataEntryRecordField(
			@NonNull final DataEntryFieldId dataEntryFieldId,
			@NonNull final CreatedUpdatedInfo createdUpdatedInfo,
			@Nullable final T value)
	{
		final DataEntryRecordField<T> result;

		if (FieldType.TEXT.getClazz().isInstance(value))
		{
			assume(FieldType.TEXT.getClazz().equals(String.class), "FieldType.TEXT's class needs to be {}", String.class);
			result = (DataEntryRecordField<T>)DataEntryRecordFieldString.of(dataEntryFieldId, createdUpdatedInfo, (String)value);
		}
		else if (FieldType.LONG_TEXT.getClazz().isInstance(value))
		{
			assume(FieldType.LONG_TEXT.getClazz().equals(String.class), "FieldType.LONG_TEXT's class needs to be {}", String.class);
			result = (DataEntryRecordField<T>)DataEntryRecordFieldString.of(dataEntryFieldId, createdUpdatedInfo, (String)value);
		}
		else if (FieldType.NUMBER.getClazz().isInstance(value))
		{
			assume(FieldType.NUMBER.getClazz().equals(BigDecimal.class), "FieldType.NUMBER's class needs to be {}", BigDecimal.class);
			result = (DataEntryRecordField<T>)DataEntryRecordFieldNumber.of(dataEntryFieldId, createdUpdatedInfo, (BigDecimal)value);
		}
		else if (FieldType.LIST.getClazz().isInstance(value))
		{
			assume(FieldType.LIST.getClazz().equals(DataEntryListValueId.class), "FieldType.LIST's class needs to be {}", DataEntryListValueId.class);
			result = (DataEntryRecordField<T>)DataEntryRecordFieldListValue.of(dataEntryFieldId, createdUpdatedInfo, (DataEntryListValueId)value);
		}
		else if (FieldType.DATE.getClazz().isInstance(value))
		{
			assume(FieldType.DATE.getClazz().equals(LocalDate.class), "FieldType.DATE's class needs to be {}", LocalDate.class);
			result = (DataEntryRecordField<T>)DataEntryRecordFieldDate.of(dataEntryFieldId, createdUpdatedInfo, (LocalDate)value);
		}
		else if (FieldType.YESNO.getClazz().isInstance(value))
		{
			assume(FieldType.YESNO.getClazz().equals(Boolean.class), "FieldType.YESNO's class needs to be {}", Boolean.class);
			result = (DataEntryRecordField<T>)DataEntryRecordFieldYesNo.of(dataEntryFieldId, createdUpdatedInfo, (Boolean)value);
		}
		else
		{
			fail("Unexpected value type={}; dataEntryFieldId={}", value.getClass(), dataEntryFieldId);
			result = null;
		}

		return result;
	}
}