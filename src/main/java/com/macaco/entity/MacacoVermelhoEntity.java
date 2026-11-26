package com.macaco.entity;

import com.macaco.MacacoMod;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class MacacoVermelhoEntity extends TamableAnimal {

	public MacacoVermelhoEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
		super(entityType, level);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Animal.createAnimalAttributes()
				.add(Attributes.MAX_HEALTH, 10.0)
				.add(Attributes.MOVEMENT_SPEED, 0.32);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
		this.goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F));
		this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0));
		this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return stack.is(Items.SWEET_BERRIES)
				|| stack.is(Items.APPLE)
				|| stack.is(Items.CARROT);
	}

	@Override
	public InteractionResult mobInteract(Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);

		if (this.isFood(stack)) {
			if (!this.isTame()) {
				if (!this.level().isClientSide()) {
					if (this.random.nextInt(3) == 0 && !player.getAbilities().instabuild) {
						this.tame(player);
						this.setOrderedToSit(true);
						this.level().broadcastEntityEvent(this, (byte) 7);
						stack.shrink(1);
					} else {
						this.playAmbientSound();
					}
				}
				return InteractionResult.SUCCESS;
			}

			if (this.isOwnedBy(player) && this.getHealth() < this.getMaxHealth()) {
				if (!this.level().isClientSide()) {
					this.heal(2.0F);
					stack.shrink(1);
				}
				return InteractionResult.SUCCESS;
			}
		}

		if (!stack.isEmpty() && player.isShiftKeyDown() && this.isOwnedBy(player)) {
			if (!this.level().isClientSide()) {
				this.setOrderedToSit(!this.isOrderedToSit());
				this.navigation.stop();
				this.setTarget(null);
			}
			return InteractionResult.SUCCESS;
		}

		return super.mobInteract(player, hand);
	}

	@Override
	public MacacoVermelhoEntity getBreedOffspring(ServerLevel level, AgeableMob partner) {
		return new MacacoVermelhoEntity(MacacoMod.MACACO_VERMELHO, level);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.PARROT_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(net.minecraft.world.damagesource.DamageSource source) {
		return SoundEvents.PARROT_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.PARROT_DEATH;
	}

	@Override
	protected void playEatingSound() {
		this.playSound(SoundEvents.PARROT_EAT, 0.5F, this.getVoicePitch());
	}
}
