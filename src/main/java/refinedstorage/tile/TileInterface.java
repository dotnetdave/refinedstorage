package refinedstorage.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import refinedstorage.inventory.InventorySimple;
import refinedstorage.util.InventoryUtils;

public class TileInterface extends TileMachine implements ISidedInventory
{
	private InventorySimple inventory = new InventorySimple("interface", 9 * 3);

	@Override
	public int getEnergyUsage()
	{
		return 5;
	}

	@Override
	public void updateMachine()
	{
		for (int i = 0; i < 9; ++i)
		{
			if (inventory.getStackInSlot(i) != null)
			{
				ItemStack slot = inventory.getStackInSlot(i);

				if (getController().push(slot))
				{
					inventory.setInventorySlotContents(i, null);
				}
			}
		}

		for (int i = 9; i < 18; ++i)
		{
			ItemStack wanted = inventory.getStackInSlot(i);
			ItemStack got = inventory.getStackInSlot(i + 9);

			if (wanted != null)
			{
				boolean ok = false;

				if (got != null)
				{
					if (!InventoryUtils.compareStackNoQuantity(wanted, got))
					{
						if (getController().push(got))
						{
							inventory.setInventorySlotContents(i + 9, null);
						}
					}
					else
					{
						ok = true;
					}
				}
				else
				{
					ok = true;
				}

				if (ok)
				{
					got = inventory.getStackInSlot(i + 9);

					int needed = got == null ? wanted.stackSize : wanted.stackSize - got.stackSize;

					ItemStack goingToTake = wanted.copy();
					goingToTake.stackSize = needed;

					ItemStack took = getController().take(goingToTake);

					if (took != null)
					{
						if (got == null)
						{
							inventory.setInventorySlotContents(i + 9, took);
						}
						else
						{
							got.stackSize += took.stackSize;
						}
					}
				}
			}
			else if (got != null)
			{
				if (getController().push(got))
				{
					inventory.setInventorySlotContents(i + 9, null);
				}
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);

		InventoryUtils.restoreInventory(this, 0, nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		InventoryUtils.saveInventory(this, 0, nbt);
	}

	@Override
	public int getSizeInventory()
	{
		return inventory.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return inventory.getStackInSlot(slot);
	}

	@Override
	public ItemStack decrStackSize(int slot, int count)
	{
		return inventory.decrStackSize(slot, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int slot)
	{
		return inventory.removeStackFromSlot(slot);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		inventory.setInventorySlotContents(slot, stack);
	}

	@Override
	public int getInventoryStackLimit()
	{
		return inventory.getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player)
	{
		return inventory.isUseableByPlayer(player);
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
		inventory.openInventory(player);
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
		inventory.closeInventory(player);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		return inventory.isItemValidForSlot(slot, stack);
	}

	@Override
	public int getField(int id)
	{
		return inventory.getField(id);
	}

	@Override
	public void setField(int id, int value)
	{
		inventory.setField(id, value);
	}

	@Override
	public int getFieldCount()
	{
		return inventory.getFieldCount();
	}

	@Override
	public void clear()
	{
		inventory.clear();
	}

	@Override
	public String getName()
	{
		return inventory.getName();
	}

	@Override
	public boolean hasCustomName()
	{
		return inventory.hasCustomName();
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return inventory.getDisplayName();
	}

	@Override
	public IInventory getDroppedInventory()
	{
		return inventory;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		if (side == EnumFacing.DOWN)
		{
			return new int[]
			{
				18, 19, 20, 21, 22, 23, 24, 25, 26
			};
		}

		return new int[]
		{
			0, 1, 2, 3, 4, 5, 6, 7, 8
		};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side)
	{
		return slot < 9;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, EnumFacing side)
	{
		return slot >= 18;
	}
}
