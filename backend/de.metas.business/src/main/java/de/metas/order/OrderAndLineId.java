package de.metas.order;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import org.slf4j.Logger;

import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
public class OrderAndLineId
{
	private static final Logger logger = LogManager.getLogger(OrderAndLineId.class);

	public static OrderAndLineId ofRepoIdsOrNull(final int orderRepoId, final int orderLineRepoId)
	{
		if (orderLineRepoId <= 0)
		{
			return null;
		}
		if (orderRepoId <= 0)
		{
			logger.warn("ofRepoIdsOrNull: Possible development error: orderLineRepoId={} while orderRepoId={}. Returning null", orderLineRepoId, orderRepoId);
			return null;
		}
		return ofRepoIds(orderRepoId, orderLineRepoId);
	}

	public static OrderAndLineId ofRepoIds(final int orderRepoId, final int orderLineRepoId)
	{
		return new OrderAndLineId(OrderId.ofRepoId(orderRepoId), OrderLineId.ofRepoId(orderLineRepoId));
	}

	public static OrderAndLineId of(final OrderId orderId, final OrderLineId orderLineId)
	{
		return new OrderAndLineId(orderId, orderLineId);
	}

	public static int getOrderRepoIdOr(final OrderAndLineId orderAndLineId, final int defaultValue)
	{
		return orderAndLineId != null ? orderAndLineId.getOrderRepoId() : defaultValue;
	}

	public static int toOrderLineRepoId(final OrderAndLineId orderAndLineId)
	{
		return getOrderLineRepoIdOr(orderAndLineId, -1);
	}

	public static int getOrderLineRepoIdOr(final OrderAndLineId orderAndLineId, final int defaultValue)
	{
		return orderAndLineId != null ? orderAndLineId.getOrderLineRepoId() : defaultValue;
	}

	public static Set<Integer> getOrderLineRepoIds(final Collection<OrderAndLineId> orderAndLineIds)
	{
		return orderAndLineIds.stream().map(OrderAndLineId::getOrderLineRepoId).collect(ImmutableSet.toImmutableSet());
	}

	OrderId orderId;
	OrderLineId orderLineId;

	private OrderAndLineId(@NonNull final OrderId orderId, @NonNull final OrderLineId orderLineId)
	{
		this.orderId = orderId;
		this.orderLineId = orderLineId;
	}

	public int getOrderRepoId()
	{
		return orderId.getRepoId();
	}

	public int getOrderLineRepoId()
	{
		return orderLineId.getRepoId();
	}

	public static boolean equals(final OrderAndLineId o1, final OrderAndLineId o2)
	{
		return Objects.equals(o1, o2);
	}

}
