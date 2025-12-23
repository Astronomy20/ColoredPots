package net.astronomy.coloredpots.datagen;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashCode;
import net.astronomy.coloredpots.ColoredPots;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModTextureGenerator implements DataProvider {

    private static final Map<DyeColor, int[]> TERRACOTTA_COLORS = new HashMap<>();

    static {
        TERRACOTTA_COLORS.put(DyeColor.WHITE, new int[]{210, 177, 161});
        TERRACOTTA_COLORS.put(DyeColor.ORANGE, new int[]{157, 81, 35});
        TERRACOTTA_COLORS.put(DyeColor.MAGENTA, new int[]{148, 86, 108});
        TERRACOTTA_COLORS.put(DyeColor.LIGHT_BLUE, new int[]{118, 111, 140});
        TERRACOTTA_COLORS.put(DyeColor.YELLOW, new int[]{184, 130, 33});
        TERRACOTTA_COLORS.put(DyeColor.LIME, new int[]{103, 117, 53});
        TERRACOTTA_COLORS.put(DyeColor.PINK, new int[]{166, 81, 81});
        TERRACOTTA_COLORS.put(DyeColor.GRAY, new int[]{58, 43, 36});
        TERRACOTTA_COLORS.put(DyeColor.LIGHT_GRAY, new int[]{135, 107, 98});
        TERRACOTTA_COLORS.put(DyeColor.CYAN, new int[]{86, 92, 92});
        TERRACOTTA_COLORS.put(DyeColor.PURPLE, new int[]{123, 74, 89});
        TERRACOTTA_COLORS.put(DyeColor.BLUE, new int[]{74, 59, 91});
        TERRACOTTA_COLORS.put(DyeColor.BROWN, new int[]{79, 53, 36});
        TERRACOTTA_COLORS.put(DyeColor.GREEN, new int[]{78, 85, 44});
        TERRACOTTA_COLORS.put(DyeColor.RED, new int[]{141, 58, 45});
        TERRACOTTA_COLORS.put(DyeColor.BLACK, new int[]{37, 23, 16});
    }

    private final PackOutput.PathProvider blockPathProvider;
    private final PackOutput.PathProvider itemPathProvider;
    private final ExistingFileHelper existingFileHelper;

    public ModTextureGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        this.blockPathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "textures/block");
        this.itemPathProvider = output.createPathProvider(PackOutput.Target.RESOURCE_PACK, "textures/item");
        this.existingFileHelper = existingFileHelper;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return CompletableFuture.runAsync(() -> {
            try {
                BufferedImage vanillaBlockPot = loadVanillaTexture("block/flower_pot");
                BufferedImage vanillaItemPot = loadVanillaTexture("item/flower_pot");

                for (DyeColor color : DyeColor.values()) {
                    BufferedImage coloredBlockPot = applyColorTint(vanillaBlockPot, color);
                    BufferedImage coloredItemPot = applyColorTint(vanillaItemPot, color);

                    saveBlockTexture(cache, color, coloredBlockPot);
                    saveItemTexture(cache, color, coloredItemPot);
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to generate textures", e);
            }
        });
    }

    private BufferedImage loadVanillaTexture(String texturePath) throws IOException {
        ResourceLocation vanillaPotLoc = ResourceLocation.withDefaultNamespace(texturePath);

        try (InputStream is = existingFileHelper.getResource(vanillaPotLoc,
                net.minecraft.server.packs.PackType.CLIENT_RESOURCES, ".png", "textures").open()) {
            return ImageIO.read(is);
        } catch (Exception e) {
            throw new IOException("Could not find vanilla " + texturePath + ".png texture", e);
        }
    }

    private BufferedImage applyColorTint(BufferedImage source, DyeColor dyeColor) {
        int[] targetColor = TERRACOTTA_COLORS.get(dyeColor);

        int width = source.getWidth();
        int height = source.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = source.getRGB(x, y);
                int alpha = (pixel >> 24) & 0xFF;

                if (alpha == 0) {
                    result.setRGB(x, y, pixel);
                    continue;
                }

                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;

                // Calculate brightness factor (0.5 to 1.5 range for better brightness preservation)
                float brightness = (red * 0.299f + green * 0.587f + blue * 0.114f) / 255f;
                // Adjust brightness curve to keep colors brighter
                brightness = 0.5f + brightness;

                // Apply terracotta color with enhanced brightness
                int newRed = (int) (targetColor[0] * brightness);
                int newGreen = (int) (targetColor[1] * brightness);
                int newBlue = (int) (targetColor[2] * brightness);

                // Clamp values
                newRed = Math.min(255, Math.max(0, newRed));
                newGreen = Math.min(255, Math.max(0, newGreen));
                newBlue = Math.min(255, Math.max(0, newBlue));

                int newPixel = (alpha << 24) | (newRed << 16) | (newGreen << 8) | newBlue;
                result.setRGB(x, y, newPixel);
            }
        }

        return result;
    }

    private void saveBlockTexture(CachedOutput cache, DyeColor color, BufferedImage image) throws IOException {
        String filename = color.getName() + "_flower_pot.png";
        Path outputPath = blockPathProvider.json(ResourceLocation.fromNamespaceAndPath(
                ColoredPots.MOD_ID, filename.replace(".png", "")));

        // Change extension from .json to .png
        outputPath = outputPath.getParent().resolve(filename);

        saveImage(cache, outputPath, image);
    }

    private void saveItemTexture(CachedOutput cache, DyeColor color, BufferedImage image) throws IOException {
        String filename = color.getName() + "_flower_pot.png";
        Path outputPath = itemPathProvider.json(ResourceLocation.fromNamespaceAndPath(
                ColoredPots.MOD_ID, filename.replace(".png", "")));

        // Change extension from .json to .png
        outputPath = outputPath.getParent().resolve(filename);

        saveImage(cache, outputPath, image);
    }

    private void saveImage(CachedOutput cache, Path outputPath, BufferedImage image) throws IOException {
        java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
        ImageIO.write(image, "PNG", baos);
        byte[] imageBytes = baos.toByteArray();

        HashCode hash = Hashing.sha1().hashBytes(imageBytes);
        cache.writeIfNeeded(outputPath, imageBytes, hash);
    }

    @Override
    public String getName() {
        return "Colored Flower Pot Textures";
    }
}