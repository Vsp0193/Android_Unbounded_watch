package com.unbounded.realizingself.data.remote.retrofitapi
import com.unbounded.realizingself.model.segment.MeditationRoutineSegmentResponse
import com.unbounded.realizingself.model.segment.segmets.timer.TimeSegmentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("preset/user")
    suspend fun getMeditationRoutineList(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Query("userId") id: Int,
        @Query("useFullDescription") useFullDescription: Boolean=false,
    ): Response<MeditationRoutineSegmentResponse?>

    @POST("preset/use")
    suspend fun activeMeditationRoutineList(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Body request: Map<String, String>,
    ): Response<MeditationRoutineSegmentResponse?>


    @GET("time-segment/user")
    suspend fun getMeditationSegmentList(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Query("userId") id: Int,
    ): Response<TimeSegmentResponse?>
   /* @POST("user/login")
    suspend fun logIn(
        @Header("Content-Type") contentType: String,
        @Body postData: PostData,
    ): Response<LogInResultModel>

    @POST("user/signup")
    suspend fun signUp(
        @Header("Content-Type") contentType: String,
        @Body request: Map<String, String>,
        ): Response<SignUpResultModel>


    @POST("user/mobile/password/forgot")
    suspend fun forgotPassword(
        @Header("Content-Type") contentType: String,
        @Body request: Map<String, String>,

        ): Response<ForgotModelResult>

    @POST("user/otp/verify")
    suspend fun otp(
        @Header("Content-Type") contentType: String,
        @Body request: Map<String, String>,

        ): Response<ForgotModelResult>

    @POST("reminder/create")
    suspend fun createReminder(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") Authorization: String,
        @Body request: ReminderRequest,

        ): Response<CreateReminderResponseModel>

    @GET("reminder/all")
    suspend fun getReminder(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") Authorization: String,

        ): Response<AllreminderResponseModel>

    @POST("reminder/delete")
    suspend fun deleteReminder(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") Authorization: String,
        @Body request: DeleteReminderRequest,
    ): Response<DeleteReminderResponseModel>

    @POST("reminder/{id}/activate")
    suspend fun activateReminder(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") Authorization: String,
        @Path("id") id: Int,
    ): Response<DeleteReminderResponseModel>

    @POST("reminder/{id}/deactivate")
    suspend fun deactivateReminder(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") Authorization: String,
        @Path("id") id: Int,
    ): Response<DeleteReminderResponseModel>

    @GET("user/info")
    suspend fun getUserInfo(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") Authorization: String,
        @Query("userId") id: String,
    ): Response<LogInResultModel>

    @Multipart
    @POST("user/edit/profile-image")
    suspend fun sendProfileImage(
        @Header("Authorization") authorization: String,
        @Part file: MultipartBody.Part?,
    ): Response<ProfileImageResponse?>

    @GET("time-segment/user")
    suspend fun getSegmentData(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") Authorization: String,
        @Query("userId") id: String,
    ): Response<HomeSegmentDataModel>

    @POST("user/password/change")
    suspend fun changePassword(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Body request: ChangePasswordRequest,
    ): Response<DeleteReminderResponseModel?>
    @POST("user/trainer/delete-user")
    suspend fun deleteAccount(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Body request: Map<String, String>,
    ): Response<DeleteReminderResponseModel?>
    @POST("user/info/edit")
    suspend fun updateUserInfo(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Body request: Map<String, String>,
    ): Response<UpdateProfileResponse?>
    @POST("user/remove-push-token")
    suspend fun logout(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Body request: Map<String, String>,
    ): Response<UpdateProfileResponse?>
    @GET("group/{userId}/users")
    suspend fun getAllUserProfile(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Path("userId") id: Int,
    ): Response<AllUserProfile?>

    @POST("group/create")
    suspend fun createGroup(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Body request: CreateGroupRequest,
    ): Response<CreateGroupResponse?>

    @GET("group/user/{userId}")
    suspend fun getAllGroup(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Path("userId") id: Int,
    ): Response<GroupListResponse?>
    @GET("group/user/{userId}/group-invite")
    suspend fun getAllInvitationGroup(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Path("userId") id: Int,
    ): Response<InvitationListResponse?>
    @POST("group/user/{userId}/group-invite/accept")
    suspend fun acceptInvitationGroup(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Path("userId") id: Int,
        @Body request: Map<String, String>,
    ): Response<DeclineInvitationResponse?>
    @POST("group/user/{userId}/group-invite/decline")
    suspend fun declineInvitationGroup(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Path("userId") id: Int,
        @Body request: Map<String, String>,
    ): Response<DeclineInvitationResponse?>
    @GET("meditation/stats/extended")
    suspend fun meditationStatsExtended(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Header("timeZone") timeZone: String,
        @Query("userId") id: Int,
    ): Response<MeditationExtendedModel?>
    @GET("meditation/practice/monthly")
    suspend fun meditationPracticeMonthly(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Header("timeZone") timeZone: String,
        @Query("userId") id: Int,
        @Query("date") date: String,
    ): Response<MeditationWeeklyModel?>
    @GET("meditation/practice/weekly")
    suspend fun meditationPracticeWeekly(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Header("timeZone") timeZone: String,
        @Query("userId") id: Int,
        @Query("date") date: String,
    ): Response<MeditationWeeklyModel?>
    @GET("meditation/session/monthly")
    suspend fun meditationSessionMonthly(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Header("timeZone") timeZone: String,
        @Query("userId") id: Int,
        @Query("date") date: String,
    ): Response<TimeMeditationModel?>
    @GET("meditation/session/weekly")
    suspend fun meditationSessionWeekly(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Header("timeZone") timeZone: String,
        @Query("userId") id: Int,
        @Query("date") date: String,
    ): Response<TimeMeditationModel?>

    @POST("meditation/create")
    suspend fun createMeditation(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Header("timeZone") timeZone: String,
        @Body request: Map<String, String>,
    ): Response<CreateMeditationResponse?>

    @GET("meditation/user")
    suspend fun getSessionList(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Query("userId") id: Int,
    ): Response<SessionResponse?>
    @GET("meditation/notes")
    suspend fun getNotesList(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Query("userId") id: Int,
    ): Response<GetNotesResponse?>

    @POST("meditation/add-note")
    suspend fun addMeditationNote(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Body request: DeleteNotesRequest,

        ): Response<SaveNotesResponse?>
    @POST("user/edit/setting")
    suspend fun privateProfile(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Body request: Map<String, String>,
    ): Response<PrivateProfileResponse?>
    @POST("user/mobile/password/reset")
    suspend fun resetPassword(
        @Header("Content-Type") contentType: String,
        @Header("Accept") accept: String,
        @Body request: Map<String, String>,
    ): Response<ResetPasswordResponse?>

   // @Headers("Connect-Time: 50000", "Read-Time: 50000")
    @GET("preset/user")
    suspend fun getMeditationRoutineList(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Query("userId") id: Int,
    ): Response<MeditationRoutineSegmentResponse?>
    @POST("preset/reorder-segments")
    suspend fun reOrderSegment(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Body request : ReOrderRequest,
    ): Response<ReOrderSegmentResponse?>
  //  @Headers("Connect-Time: 50000", "Read-Time: 50000")
    @GET("time-segment/user")
    suspend fun getMeditationSegmentList(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Query("userId") id: Int,
    ): Response<TimeSegmentResponse?>
    @POST("time-segment/delete")
    suspend fun deleteMeditationSegmentList(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Body request: Map<String, String>,
    ): Response<TimeSegmentResponse?>
    @POST("preset/delete")
    suspend fun deleteMeditationRoutineList(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Body request: Map<String, String>,
    ): Response<MeditationRoutineSegmentResponse?>
   // @Headers("Connect-Time: 50000", "Read-Time: 50000")
    @POST("preset/use")
    suspend fun activeMeditationRoutineList(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Body request: Map<String, String>,
    ): Response<MeditationRoutineSegmentResponse?>

    @POST("preset/save")
    suspend fun addMeditationRoutineList(
    @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Header("Accept") accept: String,
    @Body postData: SaveSegmentData,
    ): Response<SaveSegmentResponse?>
    @POST("time-segment/add")
    suspend fun addTimeSegment(
    @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
    @Body request: Map<String, String>,
    ): Response<CommonMessageResponse?>
    @POST("time-segment/edit")
    suspend fun editTimeSegment(
    @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
    @Body request: Map<String, String>,
    ): Response<CommonMessageResponse?>
    @GET("group/{groupId}/members")
    suspend fun getEditGroupList(
    @Header("Content-Type") contentType: String,
    @Header("Authorization") authorization: String,
    @Path("groupId") groupId : Int? ,
    ): Response<EditGroupListResponse?>
    @POST("group/user/leave-group")
    suspend fun deleteGroup(
    @Header("Content-Type") contentType: String,
    @Header("Authorization") authorization: String,
    @Body request: Map<String, String>,
    ): Response<CommonMessageResponse?>
    @POST("user/profile")
    suspend fun memberProfile(
    @Header("Content-Type") contentType: String,
    @Header("Authorization") authorization: String,
    @Header("timeZone") timeZone: String,
    @Body request: Map<String, String>,
    ): Response<MemberProfileResponse?>
    @GET("group/stats/{groupId}")
    suspend fun getGroupStates(
    @Header("Content-Type") contentType: String,
    @Header("Authorization") authorization: String,
    @Path("groupId") groupId : Int ,
    ): Response<GroupStatesResponse?>
    @GET("post/group/{groupId}")
    suspend fun getGroupMileStone(
    @Header("Content-Type") contentType: String,
    @Header("Authorization") authorization: String,
    @Path("groupId") groupId : Int ,
    ): Response<GroupMilestoneResponse?>
    @POST("group/invite")
    suspend fun inviteGroup(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Body request: InviteGroupRequest,
    ): Response<InviteGroupResponse?>
    @POST("group/reassign-admin")
    suspend fun reAssign(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Body request: Map<String, String>,
    ): Response<MemberProfileResponse?>
    @POST("group/user/kick-member")
    suspend fun kickOutMember(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Body request: Map<String, String>,
    ): Response<MemberProfileResponse?>

    @POST("preset/update")
    suspend fun updateRoutineName(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Body request: RoutineNameRequest,
    ): Response<ReOrderSegmentResponse?>
*/
}

data class PostData(
    val email: String,
    val password: String,
    // Add other fields as needed
)
data class SaveSegmentData(
    val description: String,
   // val timeSegments: List<UserTimeSegment>?,
    // Add other fields as needed
)
data class CommonMessageResponse(
    val message: String,
    // Add other fields as needed
)
data class ReOrderSegmentResponse(
    val success: Boolean?,
    val message: String?,
    // Add other fields as needed
)