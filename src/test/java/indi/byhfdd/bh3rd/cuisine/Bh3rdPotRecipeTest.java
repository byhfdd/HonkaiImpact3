package indi.byhfdd.bh3rd.cuisine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bh3rdPotRecipeTest {

    private static final String BERRIES = "bh3rdTestRecipeBerries";
    private static final String EXACT_VARIANT = "bh3rdTestRecipeExactVariant";
    private static final String ANY_VARIANT = "bh3rdTestRecipeAnyVariant";
    private static final Item rice = new Item();
    private static final Item flower = new Item();
    private static final Item firstBerry = new Item().setHasSubtypes(true);
    private static final Item secondBerry = new Item().setHasSubtypes(true);

    private final Map<String, List<ItemStack>> ores = new HashMap<>();

    @Before
    public void prepareOreLookup() {
        ores.put(BERRIES, Arrays.asList(new ItemStack(firstBerry, 1, 0), new ItemStack(secondBerry, 1, 2)));
        ores.put(EXACT_VARIANT, Collections.singletonList(new ItemStack(firstBerry, 1, 3)));
        ores.put(ANY_VARIANT, Collections.singletonList(new ItemStack(secondBerry, 1, OreDictionary.WILDCARD_VALUE)));
    }

    @Test
    public void cookingAcceptsEitherRegisteredBerryImplementationInAnyOrder() {
        Bh3rdPotRecipe recipe = recipe(BERRIES, rice, rice, flower);

        assertTrue(recipe.canCookedThis(new int[8], new ItemStack[] {
            new ItemStack(rice), new ItemStack(flower), new ItemStack(firstBerry), new ItemStack(rice) }, null));
        assertTrue(recipe.canCookedThis(new int[8], new ItemStack[] {
            new ItemStack(secondBerry, 1, 2), new ItemStack(rice), new ItemStack(rice), new ItemStack(flower) }, null));
    }

    @Test
    public void missingOreIngredientRejectsCookingEvenWhenOtherKeysExist() {
        Bh3rdPotRecipe recipe = recipe(BERRIES, rice, flower);
        assertFalse(recipe.canCookedThis(new int[8], new ItemStack[] {
            new ItemStack(rice), new ItemStack(flower), null, new ItemStack(rice) }, null));
    }

    @Test
    public void registeredOreDoesNotExcuseMissingConcreteIngredient() {
        Bh3rdPotRecipe recipe = recipe(BERRIES, rice, flower);
        assertFalse(recipe.hasKeyFood(new ItemStack[] { new ItemStack(firstBerry), new ItemStack(rice) }));
    }

    @Test
    public void unknownOreNameNeitherMatchesNorCreatesADictionaryEntry() {
        String missing = "bh3rdTestRecipeUnregisteredOre";
        assertFalse(ores.containsKey(missing));
        assertFalse(recipe(missing).hasKeyFood(new ItemStack[] { new ItemStack(firstBerry) }));
        assertFalse(ores.containsKey(missing));
    }

    @Test
    public void oreRegistrationRequiresItsExactMetadataUnlessItUsesWildcard() {
        assertTrue(recipe(EXACT_VARIANT).hasKeyFood(new ItemStack[] { new ItemStack(firstBerry, 1, 3) }));
        assertFalse(recipe(EXACT_VARIANT).hasKeyFood(new ItemStack[] { new ItemStack(firstBerry, 1, 4) }));
        assertTrue(recipe(ANY_VARIANT).hasKeyFood(new ItemStack[] { new ItemStack(secondBerry, 1, 4) }));
        assertTrue(recipe(ANY_VARIANT).hasKeyFood(new ItemStack[] { new ItemStack(secondBerry, 1, 19) }));
    }

    @Test
    public void concreteStackKeepsManaMetalExactMetadataAndIgnoresNbt() {
        ItemStack required = new ItemStack(firstBerry, 1, 3);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("flavor", "recipe");
        required.setTagCompound(tag);
        Bh3rdPotRecipe recipe = recipe(required);

        assertTrue(recipe.hasKeyFood(new ItemStack[] { new ItemStack(firstBerry, 12, 3) }));
        assertFalse(recipe.hasKeyFood(new ItemStack[] { new ItemStack(firstBerry, 12, 4) }));
        assertFalse(recipe(new ItemStack(firstBerry, 1, OreDictionary.WILDCARD_VALUE))
            .hasKeyFood(new ItemStack[] { new ItemStack(firstBerry, 1, 3) }));
    }

    @Test
    public void concreteItemKeysUseTheirNormalMetadataZero() {
        assertTrue(recipe(rice, flower).hasKeyFood(new ItemStack[] { new ItemStack(flower), new ItemStack(rice) }));
        assertFalse(recipe(firstBerry).hasKeyFood(new ItemStack[] { new ItemStack(firstBerry, 1, 1) }));
    }

    @Test
    public void repeatedConcreteKeysPreserveTheParentsExistenceCheck() {
        assertTrue(recipe(rice, rice).hasKeyFood(new ItemStack[] { new ItemStack(rice) }));
    }

    private Bh3rdPotRecipe recipe(Object... ingredients) {
        return new Bh3rdPotRecipe(new ItemStack(rice), new int[8], 40, ingredients,
            name -> ores.getOrDefault(name, Collections.emptyList()));
    }
}
