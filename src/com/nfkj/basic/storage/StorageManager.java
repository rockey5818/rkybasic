package com.nfkj.basic.storage;

import com.truemesh.squiggle.MatchCriteria;
import com.truemesh.squiggle.Projection;
import com.truemesh.squiggle.SelectQuery;
import com.truemesh.squiggle.Table;

import java.util.ArrayList;
import java.util.List;

public abstract class StorageManager implements StorageManagerDef
{
    private final AddPlazaIntoFavoritePromptLastShownTimeStorage addPlazaIntoFavoritePromptLastShownTimeStorage
            = new AddPlazaIntoFavoritePromptLastShownTimeStorage(this);

    public AddPlazaIntoFavoritePromptLastShownTimeStorage getAddPlazaIntoFavoritePromptLastShownTimeStorage()
    {
        return addPlazaIntoFavoritePromptLastShownTimeStorage;
    }

    private final GroupStorage groupStorage = new GroupStorage(this);
    private final RecommendedGroupListStorage recommendedGroupListStorage = new RecommendedGroupListStorage(this);

    public PlazaManagerListLastUpdateTimeStorage getPlazaManagerListLastUpdateTimeStorage()
    {
        return plazaManagerListLastUpdateTimeStorage;
    }

    public StickyConvosStorage getStickyConvosStorage()
    {
        return stickyConvosStorage;
    }

    public PlazaStickyMessageStorage getPlazaStickyMessageStorage()
    {
        return plazaStickyMessageStorage;
    }

    public PlazaChatDraftStorage getPlazaChatDraftStorage()
    {
        return plazaChatDraftStorage;
    }

    private final PlazaChatDraftStorage plazaChatDraftStorage = new PlazaChatDraftStorage(this);

    public PlazaChatConvosStorage getPlazaChatConvosStorage()
    {
        return plazaChatConvosStorage;
    }

    public PlazaChatMessageStorage getPlazaChatMessageStorage()
    {
        return plazaChatMessageStorage;
    }

    public PlazaTrendListLastUpdateTimeStorage getPlazaTrendListLastUpdateTimeStorage()
    {
        return plazaTrendListLastUpdateTimeStorage;
    }

    public PlazaChatRoomLastUpdateTimeStorage getPlazaChatRoomInfoLastUpdateTimeStorage()
    {
        return plazaChatRoomLastUpdateTimeStorage;
    }

    public PlazaInformationLastUpdateTimeStorage getPlazaInformationLastUpdateTimeStorage()
    {
        return plazaInformationLastUpdateTimeStorage;
    }

    private final PlazaInformationLastUpdateTimeStorage plazaInformationLastUpdateTimeStorage =
            new PlazaInformationLastUpdateTimeStorage(this);

    public PlazaUserListLastUpdateTimeStorage getPlazaUserListLastUpdateTimeStorage()
    {
        return plazaUserListLastUpdateTimeStorage;
    }

    public PlazaChattingUserListLastUpdateTimeStorage getPlazaChattingUserListLastUpdateTimeStorage()
    {
        return plazaChattingUserListLastUpdateTimeStorage;
    }

    public PlazaUserOperationPermissionStorage getPlazaUserOperationPermissionStorage()
    {
        return plazaUserOperationPermissionStorage;
    }

    public FavoritePlazaStorage getFavoritePlazaStorage()
    {
        return favoritePlazaStorage;
    }

    private final MyCheckChangeEntitiesStorage myCheckChangeGroupContentStorage = new MyCheckChangeEntitiesStorage(this)
    {
        @Override
        protected String getCacheTableName(final long ownerUid)
        {
            return CacheTableName.USER_ROOM_CONTENT_FOR_CHECK_CHANGE_PRE + ownerUid;
        }
    };
    private final MyCheckChangeEntitiesStorage myCheckChangeGroupPhotosStorage = new MyCheckChangeEntitiesStorage(this)
    {
        @Override
        protected String getCacheTableName(final long ownerUid)
        {
            return CacheTableName.USER_ROOM_PHOTOS_FOR_CHECK_CHANGE_PRE + ownerUid;
        }
    };
    private final MyCheckChangeEntitiesStorage myCheckChangeUserContentStorage = new MyCheckChangeEntitiesStorage(this)
    {
        @Override
        protected String getCacheTableName(final long ownerUid)
        {
            return CacheTableName.USER_USER_CONTENT_FOR_CHECK_CHANGE_PRE + ownerUid;
        }
    };
    private final MyCheckChangeEntitiesStorage myCheckChangeUserPhotosStorage = new MyCheckChangeEntitiesStorage(this)
    {
        @Override
        protected String getCacheTableName(final long ownerUid)
        {
            return CacheTableName.USER_USER_PHOTOS_FOR_CHECK_CHANGE_PRE + ownerUid;
        }
    };
    private final MyCheckChangeEntitiesStorage myCheckChangeEntitiesStorage = new MyCheckChangeEntitiesStorage(this)
    {
        @Override
        protected String getCacheTableName(final long ownerUid)
        {
            return CacheTableName.USER_UPDATE_ENTITY_FOR_CHECK_CHANGE_PRE + ownerUid;
        }
    };
    private final UserStorage userStorage = new UserStorage(this);
    private final RoomStorage roomStorage = new RoomStorage(this);
    private final OfflineMessageStorage offlineMessageStorage = new OfflineMessageStorage(this);
    private final OfflineMessageRoomStorage offlineMessageRoomStorage = new OfflineMessageRoomStorage(this);
    private final ReplyOfflineMessageStorage replyOfflineMessageStorage = new ReplyOfflineMessageStorage(this);
    private final RoomLocationStorage roomLocationStorage = new RoomLocationStorage(this);
    private final RoomCategoryStorage roomCategoryStorage = new RoomCategoryStorage(this);
    private final RoomUserStorage roomUserStorage = new RoomUserStorage(this);
    private final RoomBlockUserStorage roomBlockUserStorage = new RoomBlockUserStorage(this);
    private final TalkUserStorage talkUserStorage = new TalkUserStorage(this);
    private final OfflineMessageUserStorage offlineMessageUserStorage = new OfflineMessageUserStorage(this);
    private final UserBlockerStorage userBlockerStorage = new UserBlockerStorage(this);
    private final UserFollowerStorage userFollowerStorage = new UserFollowerStorage(this);
    private final GlobalStorage globalStorage = new GlobalStorage(this);
    private final GroupChatConvosStorage groupChatConvosStorage = new GroupChatConvosStorage(this);
    private final ThemeStorage themeStorage = new ThemeStorage(this);
    private final EmotionStorage emotionStorage = new EmotionStorage(this);
    private final MyEmotionCategoriesStorage myEmotionCategoriesStorage = new MyEmotionCategoriesStorage(this);
    private final MyEmotionCategoriesStorage myRecommandCategoriesStorage = new MyEmotionCategoriesStorage(this)
    {
        @Override
        protected String getCacheTableName(final long ownerUid)
        {
            return CacheTableName.USER_RECOMMENDED_CATEGORY + ownerUid;
        }

        @Override
        public void updateAll(final long ownerId, final List<MobileEssentialEmotionCategory> emotionCategoryList)
        {
            final List<String> queries = new ArrayList<>();
            String sql = "DELETE FROM `" + getCacheTableName(ownerId) + "`";
            queries.add(sql);
            for (final MobileEssentialEmotionCategory category : emotionCategoryList)
            {
                sql = getStoreDataSql(ownerId, category);
                if (Strings.notNullOrEmpty(sql))
                {
                    queries.add(sql);
                }
            }
            getStorageManager().executeMultiSQL(queries);
        }
    };
    private final MyRelUsersStorage myFriendsStorage = new MyRelUsersStorage(this)
    {
        @Override
        protected String getCacheTableName(final long ownerUid)
        {
            return CacheTableName.USER_FRIENDS + ownerUid;
        }
    };
    private final MyRelUsersStorage myFollowersStorage = new MyRelUsersStorage(this)
    {
        @Override
        protected String getCacheTableName(final long ownerUid)
        {
            return CacheTableName.USER_FOLLOWERS + ownerUid;
        }
    };
    private final MyRelUsersStorage myFansStorage = new MyRelUsersStorage(this)
    {
        @Override
        protected String getCacheTableName(final long ownerUid)
        {
            return CacheTableName.USER_FANS + ownerUid;
        }
    };
    private final MyFriendRecordStorage myFriendRecordStorage = new MyFriendRecordStorage(this);
    private final PrivateChatRoomStorage privateChatRoomStorage = new PrivateChatRoomStorage(this);
    private final MyErrorReportsStorage myErrorReportsStorage = new MyErrorReportsStorage(this);
    private final GroupChatRoomLightOnTimeStorage groupChatRoomLightOnTimeStorage = new GroupChatRoomLightOnTimeStorage(
            this);
    private final MyGroupTopMessagesStorage myGroupTopMessagesStorage = new MyGroupTopMessagesStorage(this);
    private final CloseNotifyMessageStorage closeNotifyMessageStorage = new CloseNotifyMessageStorage(this);
    private final UnlockEmotionMessageStorage unlockEmotionMessageStorage = new UnlockEmotionMessageStorage(this);
    private final LockSinaStorage locksinaStorage = new LockSinaStorage(this);
    private final SceneLaudersStorage sceneLaudersStorage = new SceneLaudersStorage(this);
    private final MySceneReplyReadLocationStorage mySceneReplyReadLocationStorage = new MySceneReplyReadLocationStorage(
            this);
    private final NetworkTrafficReportsStorage myNetworkTrafficReportsStorage = new NetworkTrafficReportsStorage(this);
    private final MyIgnoredSceneIdListStorage myIgnoredSceneIdListStorage = new MyIgnoredSceneIdListStorage(this);
    private final PostStorage postStorage = new PostStorage(this);
    private final PostReplyStorage postReplyStorage = new PostReplyStorage(this);
    private final PlazaPostListStorage plazaPostListStorage = new PlazaPostListStorage(this);
    private final PlazaTopPostListStorage plazaTopPostListStorage = new PlazaTopPostListStorage(this);
    private final PostDraftStorage postDraftStorage = new PostDraftStorage(this);
    private final FollowerAliasStorage followerAliasStorage = new FollowerAliasStorage(this);

    public PlazaSpaceLastUpdateTimeStorage getPlazaSpaceLastUpdateTimeStorage()
    {
        return plazaSpaceLastUpdateTimeStorage;
    }

    private final HttpRecordStorage httpRecordStorage = new HttpRecordStorage(this);

    public PlazaBlacklistStorage getPlazaBlacklistStorage()
    {
        return plazaBlacklistStorage;
    }

    public PlazaChattingUserStorage getPlazaChattingUserStorage()
    {
        return plazaChattingUserStorage;
    }

    public PlazaUserListStorage getPlazaUserListStorage()
    {
        return plazaUserListStorage;
    }

    public RecommendedPlazaListStorage getRecommendedPlazaListStorage()
    {
        return recommendedPlazaListStorage;
    }

    public PlazaTrendStorage getPlazaTrendStorage()
    {
        return plazaTrendStorage;
    }

    public PlazaStorage getPlazaStorage()
    {
        return plazaStorage;
    }

    public PlazaPhotoAlbumLastUpdateTimeStorage getPlazaPhotoAlbumLastUpdateTimeStorage()
    {
        return plazaPhotoAlbumLastUpdateTimeStorage;
    }

    public PlazaPhotoAlbumStorage getPlazaPhotoAlbumStorage()
    {
        return plazaPhotoAlbumStorage;
    }

    public EnteredPrivateChatRoomsStorage getEnteredPrivateChatRoomsStorage()
    {
        return enteredPrivateChatRoomsStorage;
    }

    public EnteredPlazaChatRoomsStorage getEnteredPlazaChatRoomsStorage()
    {
        return enteredPlazaChatRoomsStorage;
    }

    public ReceivedCustomNoticeStorage getReceivedCustomNoticeStorage()
    {
        return receivedCustomNoticeStorage;
    }

    public final UnrecognizedSceneStorage getUnrecognizedSceneStorage()
    {
        return unrecognizedSceneStorage;
    }

    /**
     * Create cache tables for current user.
     *
     * @param ownerUid User ID of the cache owner.
     */
    public void createPrivateCacheTables(final long ownerUid)
    {
        getNoticeStorage().createCacheTableIfNotExists(ownerUid);
        getGreetConvosStorage().createCacheTableIfNotExists(ownerUid);
        getPrivateChatConvosStorage().createCacheTableIfNotExists(ownerUid);
        getPrivateChatMessageStorage().createCacheTableIfNotExists(ownerUid);
        getUnrecognizedMessageStorage().createCacheTableIfNotExists(ownerUid);
        getUnrecognizedNoticeStorage().createCacheTableIfNotExists(ownerUid);
        getMyGroupsStorage().createCacheTableIfNotExists(ownerUid);
        getMyCheckChangeGroupContentStorage().createCacheTableIfNotExists(ownerUid);
        getMyCheckChangeGroupPhotosStorage().createCacheTableIfNotExists(ownerUid);
        getMyEmotionCategoriesStorage().createCacheTableIfNotExists(ownerUid);
        getMyRecommandCategoriesStorage().createCacheTableIfNotExists(ownerUid);
        getGroupChatConvosStorage().createCacheTableIfNotExists(ownerUid);
        getMyFriendsStorage().createCacheTableIfNotExists(ownerUid);
        getMyFansStorage().createCacheTableIfNotExists(ownerUid);
        getMyFollowersStorage().createCacheTableIfNotExists(ownerUid);
        getMyFriendRecordStorage().createCacheTableIfNotExists(ownerUid);
        getPrivateChatRoomStorage().createCacheTableIfNotExists(ownerUid);
        getMyCheckChangeUserContentStorage().createCacheTableIfNotExists(ownerUid);
        getMyCheckChangeUserPhotosStorage().createCacheTableIfNotExists(ownerUid);
        getMyCheckChangeUpdateEntitiesStorage().createCacheTableIfNotExists(ownerUid);
        getMyErrorReportsStorage().createCacheTableIfNotExists(ownerUid);
        getChangedGroupAnnouncementStorage().createCacheTableIfNotExists(ownerUid);
        getChangedGroupNameStorage().createCacheTableIfNotExists(ownerUid);
        getMyGroupTopMessagesStorage().createCacheTableIfNotExists(ownerUid);
        getCloseNotifyMessageStorage().createCacheTableIfNotExists(ownerUid);
        getUnlockEmotionMessageStorage().createCacheTableIfNotExists(ownerUid);
        getMyNetworkTrafficReportsStorage().createCacheTableIfNotExists(ownerUid);
        getMySceneReplyReadLocationStorage().createCacheTableIfNotExists(ownerUid);
        getMyIgnoredSceneIdListStorage().createCacheTableIfNotExists(ownerUid);
        getActivitySceneListStorage().createCacheTableIfNotExists(ownerUid);
        getHttpRecordStorage().createCacheTableIfNotExists(ownerUid);
        getPostStorage().createCacheTableIfNotExists(ownerUid);
        getPostReplyStorage().createCacheTableIfNotExists(ownerUid);
        getPlazaPostListStorage().createCacheTableIfNotExists(ownerUid);
        getPlazaTopPostListStorage().createCacheTableIfNotExists(ownerUid);
        getPostDraftStorage().createCacheTableIfNotExists(ownerUid);
        getFollowerAliasStorage().createCacheTableIfNotExists(ownerUid);
        getPlazaChatConvosStorage().createCacheTableIfNotExists(ownerUid);
    }

    // ----------------------------------------------------------------------//

    /**
     * Drop the specified cache table.
     */
    public void dropCacheTable(final String tableName)
    {
        executeSQL("DROP TABLE IF EXISTS `" + tableName + "`");
    }

    public int getCurrentDatabaseVersion()
    {
        return getDatabaseVersionStorage().get();
    }

    public DatabaseVersionStorage getDatabaseVersionStorage()
    {
        return databaseVersionStorage;
    }

    public GlobalStorage getGlobalStorage()
    {
        return globalStorage;
    }

    public GreetConvosStorage getGreetConvosStorage()
    {
        return greetConvosStorage;
    }

    public GroupChatConvosStorage getGroupChatConvosStorage()
    {
        return groupChatConvosStorage;
    }

    public LastGroupVibrationTimeStorage getLastGroupVibrationTimeStorage()
    {
        return lastGroupVibrationTimeStorage;
    }

    public LastPlazaVibrationTimeStorage getLastPlazaVibrationTimeStorage()
    {
        return lastPlazaVibrationTimeStorage;
    }

    public MyGroupsStorage getMyGroupsStorage()
    {
        return myGroupsStorage;
    }

    public MyCheckChangeEntitiesStorage getMyCheckChangeGroupContentStorage()
    {
        return myCheckChangeGroupContentStorage;
    }

    public MyCheckChangeEntitiesStorage getMyCheckChangeGroupPhotosStorage()
    {
        return myCheckChangeGroupPhotosStorage;
    }

    public MyCheckChangeEntitiesStorage getMyCheckChangeUserContentStorage()
    {
        return myCheckChangeUserContentStorage;
    }

    public MyCheckChangeEntitiesStorage getMyCheckChangeUserPhotosStorage()
    {
        return myCheckChangeUserPhotosStorage;
    }

    public MyCheckChangeEntitiesStorage getMyCheckChangeUpdateEntitiesStorage()
    {
        return myCheckChangeEntitiesStorage;
    }

    public NoticeStorage getNoticeStorage()
    {
        return noticeStorage;
    }

    public OfflineMessageRoomStorage getOfflineMessageRoomStorage()
    {
        return offlineMessageRoomStorage;
    }

    public OfflineMessageStorage getOfflineMessageStorage()
    {
        return offlineMessageStorage;
    }

    public OfflineMessageUserStorage getOfflineMessageUserStorage()
    {
        return offlineMessageUserStorage;
    }

    public PrivateChatConvosStorage getPrivateChatConvosStorage()
    {
        return privateChatConvosStorage;
    }

    public UnrecognizedNoticeStorage getUnrecognizedNoticeStorage()
    {
        return unrecognizedNoticeStorage;
    }

    public UnrecognizedMessageStorage getUnrecognizedMessageStorage()
    {
        return unrecognizedMessageStorage;
    }

    public GroupChatMessageStorage getGroupChatMessageStorage()
    {
        return groupChatMessageStorage;
    }

    public PrivateChatMessageStorage getPrivateChatMessageStorage()
    {
        return pvtChatMsgSt;
    }

    public InvolvedVibrationStorage getInvolvedVibrationStorage()
    {
        return involvedVibrationStorage;
    }

    public InDazeGroupsStorage getInDazeGroupsStorage()
    {
        return inDazeGroupsStorage;
    }

    public ReplyOfflineMessageStorage getReplyOfflineMessageStorage()
    {
        return replyOfflineMessageStorage;
    }

    public GroupEssentialInfoStorage getGroupEssentialInformationStorage()
    {
        return groupEssentialInformationStorage;
    }

    public RoomCategoryStorage getRoomCategoryStorage()
    {
        return roomCategoryStorage;
    }

    public RoomLocationStorage getRoomLocationStorage()
    {
        return roomLocationStorage;
    }

    public RoomStorage getRoomStorage()
    {
        return roomStorage;
    }

    public RoomUserStorage getRoomUserStorage()
    {
        return roomUserStorage;
    }

    public RoomBlockUserStorage getRoomBlockUserStorage()
    {
        return roomBlockUserStorage;
    }

    public TalkUserStorage getTalkUserStorage()
    {
        return talkUserStorage;
    }

    public UserBlockerStorage getUserBlockerStorage()
    {
        return userBlockerStorage;
    }

    public UserEssentialInfoStorage getUserEssInfoSt()
    {
        return userEssInfoSt;
    }

    public UserGroupRankStorage getUserGroupRankStorage()
    {
        return userGroupRankStorage;
    }

    public UserFollowerStorage getUserFollowerStorage()
    {
        return userFollowerStorage;
    }

    public UserStorage getUserStorage()
    {
        return userStorage;
    }

    public EmotionStorage getEmotionStorage()
    {
        return emotionStorage;
    }

    public ThemeStorage getThemeStorage()
    {
        return themeStorage;
    }

    public MyEmotionCategoriesStorage getMyEmotionCategoriesStorage()
    {
        return myEmotionCategoriesStorage;
    }

    public MyEmotionCategoriesStorage getMyRecommandCategoriesStorage()
    {
        return myRecommandCategoriesStorage;
    }

    public MyRelUsersStorage getMyFriendsStorage()
    {
        return myFriendsStorage;
    }

    public MyRelUsersStorage getMyFollowersStorage()
    {
        return myFollowersStorage;
    }

    public MyRelUsersStorage getMyFansStorage()
    {
        return myFansStorage;
    }

    public MyFriendRecordStorage getMyFriendRecordStorage()
    {
        return myFriendRecordStorage;
    }

    public PrivateChatRoomStorage getPrivateChatRoomStorage()
    {
        return privateChatRoomStorage;
    }

    public GroupDazeUsersStorage getGroupDazeUsersStorage()
    {
        return groupDazeUsersStorage;
    }

    public GroupChatLastReadCursorStorage getGroupChatLastReadCursorStorage()
    {
        return groupChatLastReadCursorStorage;
    }

    public NearbySceneListStorage getNearbySceneListStorage()
    {
        return nearbySceneListStorage;
    }

    public FriendsSceneListStorage getFriendsSceneListStorage()
    {
        return friendsSceneListStorage;
    }

    public OthersSceneListStorage getOthersSceneListStorage()
    {
        return othersSceneListStorage;
    }

    public SceneReplyStorage getSceneReplyStorage()
    {
        return sceneReplyStorage;
    }

    public OthersSceneListLastUpdateTimeStorage getOthersSceneListLastUpdateTimeStorage()
    {
        return othersSceneListLastUpdateTimeStorage;
    }

    public SceneReplyListLastUpdateTimeStorage getSceneReplyListLastUpdateTimeStorage()
    {
        return sceneReplyListLastUpdateTimeStorage;
    }

    public MySceneReplyListStorage getMySceneReplyListStorage()
    {
        return mySceneReplyListStorage;
    }

    public GroupDazeKingStorage getGroupDazeKingStorage()
    {
        return groupDazeKingStorage;
    }

    public MyErrorReportsStorage getMyErrorReportsStorage()
    {
        return myErrorReportsStorage;
    }

    public MyGroupTopMessagesStorage getMyGroupTopMessagesStorage()
    {
        return myGroupTopMessagesStorage;
    }

    public NetworkTrafficReportsStorage getMyNetworkTrafficReportsStorage()
    {
        return myNetworkTrafficReportsStorage;
    }

    // just for clear mem cache content
    public void clear()
    {
        // It should be empty, because the table can not be dropped
    }

    /**
     * Check if table exists.
     *
     * @param tableName Name of the table.
     * @return {@code True} if table exists, otherwise {@code false}.
     */
    public boolean isTableExists(final String tableName)
    {
        Table table = new Table("sqlite_master");
        SelectQuery query = new SelectQuery(table);
        query.setProjection(Projection.COUNT);
        query.addCriteria(new MatchCriteria(table, "type", MatchCriteria.EQUALS, "table"));
        query.addCriteria(new MatchCriteria(table, "name", MatchCriteria.EQUALS, tableName));
        return 0 < Integer.parseInt(executeSQLForString(query.toString()));
    }

    /**
     * Remove all cache tables.
     */
    public void removeAllCacheTables()
    {
        List<String> tableNameSet = getNameOfAllCacheTables();
        for (String tableName : tableNameSet)
        {
            dropCacheTable(tableName);
        }
    }

    /**
     * Store current database version into cache.
     */
    public void storeCurrentDatabaseVersion()
    {
        getDatabaseVersionStorage().store(MobileClientSettings.CURRENT_DATABASE_VERSION);
    }

    /**
     * Store database version into cache.
     *
     * @param version Version of the database storage.
     */
    public void storeDatabaseVersion(final int version)
    {
        getDatabaseVersionStorage().store(version);
    }

    /**
     * Get all cache table names.
     *
     * @return Cache table name set contains all cache tables.
     */
    private List<String> getNameOfAllCacheTables()
    {
        final Table table = new Table("sqlite_master");
        final SelectQuery query = new SelectQuery(table);
        query.addColumn(table, "name");
        query.addCriteria(new MatchCriteria(table, "type", MatchCriteria.EQUALS, "table"));
        query.addCriteria(new MatchCriteria(table, "name", MatchCriteria.LIKE, CoreCacheTableName.COMMON_PREFIX + "%"));
        return executeSQLForList(query.toString());
    }

    public GroupChatRoomLightOnTimeStorage getGroupChatRoomLightOnTimeStorage()
    {
        return groupChatRoomLightOnTimeStorage;
    }

    public ChangedGroupAnnouncementStorage getChangedGroupAnnouncementStorage()
    {
        return changedGroupAnnouncementStorage;
    }

    public ChangedGroupNameStorage getChangedGroupNameStorage()
    {
        return changedGroupNameStorage;
    }

    public LastFriendVibrationTimeStorage getLastFriendVibrationTimeStorage()
    {
        return lastFriendVibrationTimeStorage;
    }

    public SceneStorage getSceneStorage()
    {
        return sceneStorage;
    }

    public GroupChatDraftStorage getGroupChatDraftStorage()
    {
        return groupChatDraftStorage;
    }

    public CloseNotifyMessageStorage getCloseNotifyMessageStorage()
    {
        return closeNotifyMessageStorage;
    }

    public UnlockEmotionMessageStorage getUnlockEmotionMessageStorage()
    {
        return unlockEmotionMessageStorage;
    }

    public LockSinaStorage getLockSinaStroage()
    {
        return locksinaStorage;
    }

    public PrivateChatDraftStorage getPrivateChatDraftStorage()
    {
        return privateChatDraftStorage;
    }

    public TopSceneListStorage getTopSceneListStorage()
    {
        return topSceneListStorage;
    }

    public ActivitySceneListStorage getActivitySceneListStorage()
    {
        return activitySceneListStorage;
    }

    public SceneLaudersStorage getSceneLaudersStorage()
    {
        return sceneLaudersStorage;
    }

    public MySceneReplyReadLocationStorage getMySceneReplyReadLocationStorage()
    {
        return mySceneReplyReadLocationStorage;
    }

    public MyIgnoredSceneIdListStorage getMyIgnoredSceneIdListStorage()
    {
        return myIgnoredSceneIdListStorage;
    }

    public HttpRecordStorage getHttpRecordStorage()
    {
        return httpRecordStorage;
    }

    public PostStorage getPostStorage()
    {
        return postStorage;
    }

    public PostReplyStorage getPostReplyStorage()
    {
        return postReplyStorage;
    }

    public PlazaPostListStorage getPlazaPostListStorage()
    {
        return plazaPostListStorage;
    }

    public PlazaTopPostListStorage getPlazaTopPostListStorage()
    {
        return plazaTopPostListStorage;
    }

    public PostDraftStorage getPostDraftStorage()
    {
        return postDraftStorage;
    }

    public FollowerAliasStorage getFollowerAliasStorage()
    {
        return followerAliasStorage;
    }

    public PlazaSignInPromptLastShownTimeStorage getPlazaSignInPromptLastShownTimeStorage()
    {
        return plazaSignInPromptLastShownTimeStorage;
    }

    public PlazaNewPostLastShownTimeStorage getPlazaNewPostLastShownTimeStorage()
    {
        return plazaNewPostLastShownTimeStorage;
    }

    public UserPlazaRankStorage getUserPlazaRankStorage()
    {
        return userPlazaRankStorage;
    }

    public GroupStorage getGroupStorage()
    {
        return groupStorage;
    }

    public RecommendedGroupListStorage getRecommendedGroupListStorage()
    {
        return recommendedGroupListStorage;
    }
}
