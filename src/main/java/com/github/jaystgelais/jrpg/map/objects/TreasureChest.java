package com.github.jaystgelais.jrpg.map.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.github.jaystgelais.jrpg.GameState;
import com.github.jaystgelais.jrpg.graphics.GraphicsService;
import com.github.jaystgelais.jrpg.inventory.InventoryContent;
import com.github.jaystgelais.jrpg.map.Entity;
import com.github.jaystgelais.jrpg.map.GameMap;
import com.github.jaystgelais.jrpg.map.TileCoordinate;
import com.github.jaystgelais.jrpg.map.trigger.MessageTriggerAction;
import com.github.jaystgelais.jrpg.state.State;
import com.github.jaystgelais.jrpg.state.StateAdapter;
import com.github.jaystgelais.jrpg.state.StateMachine;
import com.github.jaystgelais.jrpg.util.TimeUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class TreasureChest implements Entity {
    private final String id;
    private final GameMap map;
    private final TileCoordinate location;
    private final TreasureChestSpriteSet spriteSet;
    private final InventoryContent contents;
    private final GraphicsService graphicsService;
    private final StateMachine stateMachine;
    private final int positionX;
    private final int positionY;
    private final int height;
    private final int width;

    public TreasureChest(final String id, final GameMap map, final TileCoordinate location,
                         final TreasureChestSpriteSet spriteSet, final InventoryContent contents,
                         final GraphicsService graphicsService) {
        this.id = id;
        this.map = map;
        this.location = location;
        this.spriteSet = spriteSet;
        this.contents = contents;
        this.graphicsService = graphicsService;
        positionX = map.getAbsoluteX(location);
        positionY = map.getAbsoluteY(location);
        height = spriteSet.getSpriteHeight();
        width = spriteSet.getSpriteWidth();
        stateMachine = initStateMachine();
    }

    public void interactWith() {
        if (!isOpen()) {
            GameState.setFlag(getStateFlag(), true);
            stateMachine.change("opening");
            contents.addToInventory(GameState.getInventory(), 1);
        }
    }

    private String getStateFlag() {
        return String.format("treasurechest:%s:opened", id);
    }

    @Override
    public void update(final long elapsedTime) {
        stateMachine.update(elapsedTime);
    }

    @Override
    public void render(final GraphicsService graphicsService) {
        stateMachine.render(graphicsService);
    }

    @Override
    public boolean isOccupying(final TileCoordinate coordinate) {
        return coordinate.equals(location);
    }

    @Override
    public TileCoordinate getLocation() {
        return location;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getPositionX() {
        return positionX;
    }

    @Override
    public int getPositionY() {
        return positionY;
    }

    @Override
    public void dispose() {

    }

    private StateMachine initStateMachine() {
        Set<State> states = new HashSet<>();
        states.add(new StateAdapter() {
            @Override
            public String getKey() {
                return "closed";
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                final TextureRegion closedImage = spriteSet.getClosedImage();
                graphicsService.drawSprite(
                        closedImage,
                        positionX - (closedImage.getRegionWidth() / 2.0f), positionY
                );
            }
        });
        states.add(new StateAdapter() {
            private long timeInState = 0L;

            @Override
            public String getKey() {
                return "opening";
            }

            @Override
            public void onEnter(final Map<String, Object> params) {
                timeInState = 0L;
            }

            @Override
            public void update(final long elapsedTime) {
                timeInState += elapsedTime;
                if (TimeUtil.convertMsToFloatSeconds(timeInState)
                        > spriteSet.getOpeningAnimation().getAnimationDuration()) {
                    stateMachine.change("open");
                }
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                final TextureRegion openingFrame = spriteSet.getOpeningAnimation().getKeyFrame(
                        TimeUtil.convertMsToFloatSeconds(timeInState)
                );
                graphicsService.drawSprite(
                        openingFrame,
                        positionX - (openingFrame.getRegionWidth() / 2.0f), positionY
                );
            }

            @Override
            public void onExit() {
                map.queueAction(new MessageTriggerAction(contents.getName(), graphicsService));
            }
        });
        states.add(new StateAdapter() {
            @Override
            public String getKey() {
                return "open";
            }

            @Override
            public void render(final GraphicsService graphicsService) {
                final TextureRegion openImage = spriteSet.getOpenImage();
                graphicsService.drawSprite(
                        openImage,
                        positionX - (openImage.getRegionWidth() / 2.0f), positionY
                );
            }
        });
        return new StateMachine(states, (isOpen()) ? "open" : "closed");
    }

    private boolean isOpen() {
        return GameState.checkFlag(getStateFlag(), false);
    }
}
