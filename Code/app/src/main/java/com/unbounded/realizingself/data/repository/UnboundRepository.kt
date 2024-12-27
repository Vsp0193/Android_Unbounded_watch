package com.unbounded.realizingself.data.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject

import com.unbounded.realizingself.data.remote.retrofitapi.ApiService
import com.unbounded.realizingself.data.remote.retrofitapi.Response
import com.unbounded.realizingself.model.segment.MeditationRoutineSegmentResponse
import com.unbounded.realizingself.model.segment.segmets.timer.TimeSegmentResponse


import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnboundRepository @Inject constructor(val apiService: ApiService) {

    private fun parseErrorMessage(errorBody: String?): String? {
        return try {
            val jsonObject = Gson().fromJson(errorBody, JsonObject::class.java)
            jsonObject?.getAsJsonPrimitive("message")?.asString
        } catch (e: Exception) {
            null
        }
    }
    suspend fun getMeditationRoutineList(
        token: String,
        userId: Int
    ): com.unbounded.realizingself.data.remote.retrofitapi.Response<MeditationRoutineSegmentResponse> =
        withContext(Dispatchers.IO) {
            try {
                // Make the network call using the ApiService
                val response = apiService.getMeditationRoutineList(
                    contentType = "application/json",
                    authorization = "JWT $token",
                    id = userId
                )

                // Check if the response is successful
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        // Return Success if the body is not null
                        return@withContext com.unbounded.realizingself.data.remote.retrofitapi.Response.Success(body)
                    } ?: run {
                        // Handle the case where the body is null
                        return@withContext com.unbounded.realizingself.data.remote.retrofitapi.Response.Error(
                            statusCode = response.code(),
                            errorMessage = "Response body is null"
                        )
                    }
                } else {
                    // Handle non-successful response
                    val errorBody = response.errorBody()?.string()
                    return@withContext com.unbounded.realizingself.data.remote.retrofitapi.Response.Error(
                        statusCode = response.code(),
                        errorMessage = errorBody ?: "Unknown error"
                    )
                }
            } catch (e: Exception) {
                // Handle exceptions during the network call
                return@withContext com.unbounded.realizingself.data.remote.retrofitapi.Response.Error(
                    statusCode = 0,
                    errorMessage = e.message ?: "An unexpected error occurred"
                )
            }
        }

    suspend fun activeMeditationRoutineList(
        token:String,
        jsonMap: Map<String, String>
    ): Response<MeditationRoutineSegmentResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.activeMeditationRoutineList("application/json","JWT $token",jsonMap)
            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            Response.Error(0, e.message ?: "An error occurred")
        }
    }



    suspend fun getMeditationSegmentList(
        token:String,
        userId: Int
    ): Response<TimeSegmentResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getMeditationSegmentList("application/json","JWT $token",userId)
            Log.d("addMeditationNote", "getMeditationerror: ${response.code()}")
            if (response.isSuccessful) {
                Log.d("addMeditationNote", "getMeditationerror: ${response.code()}")
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Log.d("addMeditationNote", "getMeditationerror: ${errorBody}")
                Log.d("addMeditationNote", "getMeditationerror: ${errorMessage}")


                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            Log.d("addMeditationNote", "getMeditationerrorException: ${e.message}")

            Response.Error(0, e.message ?: "An error occurred")
        }
    }
   /* suspend fun getMeditationSegmentList(
        token:String,
        userId: Int
    ): Response<TimeSegmentResponse> = withContext(Dispatchers.IO) {

        try {
            val response = apiService.getMeditationRoutineList("application/json","JWT $token",userId)
            Log.d("addMeditationNote", "getMeditationerror: ${response.code()}")
            if (response.isSuccessful) {
                Log.d("addMeditationNote", "getMeditationerror: ${response.code()}")
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Log.d("addMeditationNote", "getMeditationerror: ${errorBody}")
                Log.d("addMeditationNote", "getMeditationerror: ${errorMessage}")
                Response.Error(response.code(), errorMessage?:"")
            }
        }catch (e: Exception){
            Log.d("addMeditationNote", "getMeditationerrorException: ${e.message}")
            Response.Error(0, e.message ?: "An error occurred")
        }
    }*/

/*
    suspend fun login(
        email: String,
        pass: String,
    ): Response<LogInResultModel> = withContext(Dispatchers.IO) {
        try {
            val postData = PostData(email, pass)
            val response = apiService.logIn("application/json", postData)

            if (response.isSuccessful) {
                Response.Success(response.body()!!)


            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
           // Response.Loading(false)
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun signUp(
        jsonMap: Map<String, String>
    ): Response<SignUpResultModel> = withContext(Dispatchers.IO) {
        try {
           // val postData = PostData(email, pass)
            val response = apiService.signUp("application/json", jsonMap)

            if (response.isSuccessful) {
                Response.Success(response.body()!!)
               // Response.Loading(false)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
           // Response.Loading(false)
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun forgotPassword(
        jsonMap: Map<String, String>
    ): Response<ForgotModelResult> = withContext(Dispatchers.IO) {
        try {
           // val postData = PostData(email, pass)
            val response = apiService.forgotPassword("application/json", jsonMap)

            if (response.isSuccessful) {
                Response.Success(response.body()!!)
               // Response.Loading(false)

            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
           // Response.Loading(false)
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun otp(
        jsonMap: Map<String, String>
    ): Response<ForgotModelResult> = withContext(Dispatchers.IO) {
        try {
           // val postData = PostData(email, pass)
            val response = apiService.otp("application/json", jsonMap)

            if (response.isSuccessful) {
                Response.Success(response.body()!!)
               // Response.Loading(false)

            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
           // Response.Loading(false)
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun createReminder(
        jsonMap: ReminderRequest,
        token:String
    ): Response<CreateReminderResponseModel> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.createReminder("application/json", "JWT $token", jsonMap)

            if (response.isSuccessful) {
                Response.Success(response.body()!!)


            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {

            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun getReminder(
        token:String
    ): Response<AllreminderResponseModel> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getReminder("application/json", "JWT $token")

            if (response.isSuccessful) {
                Response.Success(response.body()!!)

            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {

            Response.Error(0, e.message ?: "An error occurred")
        }
    }


    suspend fun deleteReminder(
        jsonMap: DeleteReminderRequest,
        token:String
    ): Response<DeleteReminderResponseModel> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.deleteReminder("application/json", "JWT $token", jsonMap)

            if (response.isSuccessful) {
                Response.Success(response.body()!!)


            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {

            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun activateReminder(
        id: Int,

        token:String
    ): Response<DeleteReminderResponseModel> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.activateReminder("application/json", "JWT $token", id)

            if (response.isSuccessful) {
                Response.Success(response.body()!!)


            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {

            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun deActivateReminder(
        id: Int,
        token:String
    ): Response<DeleteReminderResponseModel> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.deactivateReminder("application/json", "JWT $token",  id)

            if (response.isSuccessful) {
                Response.Success(response.body()!!)


            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {

            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun updateProfilePic(file: MultipartBody.Part?, token: String): Response<ProfileImageResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.sendProfileImage( "JWT $token", file!!)
            Log.d("updateProfilePiccode", "updateProfilePic: ${response.code()}")
            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage ?: "")
            }
        } catch (e: Exception) {
            Response.Error(0, e.message ?: "An error occurred")
        }
    }

    suspend fun getUserInfo(
         userId:String,
        token:String
    ): Response<LogInResultModel> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getUserInfo("application/json", "JWT $token",  userId)

            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun changePassword(
        token:String,
         request: ChangePasswordRequest
    ): Response<DeleteReminderResponseModel> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.changePassword("application/json", "JWT $token",  request)

            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            Response.Error(0, e.message ?: "An error occurred")
        }
    }



    suspend fun deleteAccount(
        jsonMap: Map<String, String>,
        token:String
    ): Response<DeleteReminderResponseModel> = withContext(Dispatchers.IO) {
        try {
            // val postData = PostData(email, pass)
            val response = apiService.deleteAccount("application/json","JWT $token", jsonMap)

            if (response.isSuccessful) {
                Response.Success(response.body()!!)
                // Response.Loading(false)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            // Response.Loading(false)
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun updateUserInfo(
        jsonMap: Map<String, String>,
        token:String
    ): Response<UpdateProfileResponse> = withContext(Dispatchers.IO) {
        try {
            // val postData = PostData(email, pass)
            val response = apiService.updateUserInfo("application/json","JWT $token", jsonMap)

            if (response.isSuccessful) {
                Response.Success(response.body()!!)
                // Response.Loading(false)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            // Response.Loading(false)
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun logout(
        jsonMap: Map<String, String>,
        token:String
    ): Response<UpdateProfileResponse> = withContext(Dispatchers.IO) {
        try {
            // val postData = PostData(email, pass)
            val response = apiService.logout("application/json","JWT $token", jsonMap)

            if (response.isSuccessful) {
                Response.Success(response.body()!!)
                // Response.Loading(false)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            // Response.Loading(false)
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun getAllMember(
        userId: Int,
        token:String,
        timezone:String
    ): Response<AllUserProfile> = withContext(Dispatchers.IO) {
        try {
            // val postData = PostData(email, pass)
            val response = apiService.getAllUserProfile("application/json","JWT $token",userId)

            if (response.isSuccessful) {
                Response.Success(response.body()!!)
                // Response.Loading(false)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            // Response.Loading(false)
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun createGroup(
        token:String,
        request: CreateGroupRequest
    ): Response<CreateGroupResponse> = withContext(Dispatchers.IO) {
        try {
            // val postData = PostData(email, pass)
            val response = apiService.createGroup("application/json","JWT $token",request)
            if (response.isSuccessful) {
                Response.Success(response.body()!!)
                // Response.Loading(false)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            // Response.Loading(false)
            Response.Error(0, e.message ?: "An error occurred")
        }
    }

    suspend fun inviteGroup(
        token:String,
        request: InviteGroupRequest
    ): Response<InviteGroupResponse> = withContext(Dispatchers.IO) {
        try {
            // val postData = PostData(email, pass)
            val response = apiService.inviteGroup("application/json","JWT $token",request)
            if (response.isSuccessful) {
                Response.Success(response.body()!!)
                // Response.Loading(false)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            // Response.Loading(false)
            Response.Error(0, e.message ?: "An error occurred")
        }
    }


    suspend fun getAllGroup(
        userId: Int,
        token:String,

    ): Response<GroupListResponse> = withContext(Dispatchers.IO) {
        try {
            // val postData = PostData(email, pass)
            val response = apiService.getAllGroup("application/json","JWT $token",userId)

            if (response.isSuccessful) {
                Response.Success(response.body()!!)
                // Response.Loading(false)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            // Response.Loading(false)
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun getAllInvitationGroup(
        userId: Int,
        token:String,

    ): Response<InvitationListResponse> = withContext(Dispatchers.IO) {
        try {
            // val postData = PostData(email, pass)
            val response = apiService.getAllInvitationGroup("application/json","JWT $token",userId)

            if (response.isSuccessful) {
                Response.Success(response.body()!!)
                // Response.Loading(false)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            // Response.Loading(false)
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun acceptInvitationGroup(
        userId: Int,
        token:String,
        jsonMap: Map<String, String>
    ): Response<DeclineInvitationResponse> = withContext(Dispatchers.IO) {
        try {
            // val postData = PostData(email, pass)
            val response = apiService.acceptInvitationGroup("application/json","JWT $token",userId,jsonMap)

            if (response.isSuccessful) {
                Response.Success(response.body()!!)
                // Response.Loading(false)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            // Response.Loading(false)
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun declineInvitationGroup(
        userId: Int,
        token:String,
        jsonMap: Map<String, String>
    ): Response<DeclineInvitationResponse> = withContext(Dispatchers.IO) {
        try {
            // val postData = PostData(email, pass)
            val response = apiService.declineInvitationGroup("application/json","JWT $token",userId,jsonMap)

            if (response.isSuccessful) {
                Response.Success(response.body()!!)
                // Response.Loading(false)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            // Response.Loading(false)
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun meditationStatsExtended(
        userId: Int,
        token:String,
        utcDifference: String,
    ): Response<MeditationExtendedModel> = withContext(Dispatchers.IO) {
        try {
            // val postData = PostData(email, pass)
            val response = apiService.meditationStatsExtended("application/json","JWT $token",utcDifference,userId)

            if (response.isSuccessful) {
                Response.Success(response.body()!!)
                // Response.Loading(false)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            // Response.Loading(false)
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun meditationPracticeWeekly(
        userId: Int,
        token:String,
        utcDifference: String,
        date:String,
    ): Response<MeditationWeeklyModel> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.meditationPracticeWeekly("application/json","JWT $token",utcDifference,userId,date)
            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun meditationPracticeMonthly(
        userId: Int,
        token:String,
        utcDifference: String,
        date:String,
    ): Response<MeditationWeeklyModel> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.meditationPracticeMonthly("application/json","JWT $token",utcDifference,userId,date)
            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun meditationSessionWeekly(
        userId: Int,
        token:String,
        utcDifference: String,
        date:String,
    ): Response<TimeMeditationModel> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.meditationSessionWeekly("application/json","JWT $token",utcDifference,userId,date)
            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun meditationSessionMonthly(
        userId: Int,
        token:String,
        utcDifference: String,
        date:String,
    ): Response<TimeMeditationModel> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.meditationSessionMonthly("application/json","JWT $token",utcDifference,userId,date)
            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun createMeditation(
        token:String,
        utcDifference: String,
        jsonMap: Map<String, String>
    ): Response<CreateMeditationResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.createMeditation("application/json","JWT $token",utcDifference,jsonMap)
            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun addMeditationNote(
        token:String,
        jsonMap: DeleteNotesRequest
    ): Response<SaveNotesResponse> = withContext(Dispatchers.IO) {
        try {

          //  Log.d("callingDelete", "callingDeletedqd-->${jsonMap.values}")
            val response = apiService.addMeditationNote("application/json","JWT $token",jsonMap)

            Log.d("callingDelete", "callingDeletedqd-->${response}")
            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Log.d( "addMeditationNote: ","errorwala-->${errorMessage}")
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            Log.d( "addMeditationNote: ","exception-->${e.message}")
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun privateProfile(
        token:String,
        jsonMap: Map<String, String>
    ): Response<PrivateProfileResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.privateProfile("application/json","JWT $token",jsonMap)
            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Log.d( "addMeditationNote: ","${errorMessage}")
                Response.Error(response.code(), errorMessage?:"")
            }
        } catch (e: Exception) {
            Log.d( "addMeditationNote: ","${e.message}")
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun resetPassword(
        jsonMap: Map<String, String>
    ): Response<ResetPasswordResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.resetPassword("application/json","application/json",jsonMap)
            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Log.d( "addMeditationNote: ","${errorMessage}")
                Response.Error(response.code(), errorMessage?:"")
            }
        } catch (e: Exception) {
            Log.d( "addMeditationNote: ","${e.message}")
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun getSessionList(
        token:String,
        userId: Int
    ): Response<SessionResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getSessionList("application/json","JWT $token",userId)
            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun getNotesList(
        token:String,
        userId: Int
    ): Response<GetNotesResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getNotesList("application/json","JWT $token",userId)
            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            Response.Error(0, e.message ?: "An error occurred")
        }
    }




    suspend fun getUserSegmentInfo(
        userId:String,
        token:String
    ): Response<HomeSegmentDataModel> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getSegmentData("application/json", "JWT $token",  userId)

            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            Response.Error(0, e.message ?: "An error occurred")
        }
    }

    suspend fun getMeditationRoutineList(
        token:String,
        userId: Int
    ): Response<MeditationRoutineSegmentResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getMeditationRoutineList("application/json","JWT $token",userId)
            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun reOrderSegment(
        token:String,
        request : ReOrderRequest,
    ): Response<ReOrderSegmentResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.reOrderSegment("application/json","JWT $token",request)
            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun getMeditationSegmentList(
        token:String,
        userId: Int
    ): Response<TimeSegmentResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getMeditationSegmentList("application/json","JWT $token",userId)
            Log.d("addMeditationNote", "getMeditationerror: ${response.code()}")
            if (response.isSuccessful) {
                Log.d("addMeditationNote", "getMeditationerror: ${response.code()}")
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Log.d("addMeditationNote", "getMeditationerror: ${errorBody}")
                Log.d("addMeditationNote", "getMeditationerror: ${errorMessage}")


                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            Log.d("addMeditationNote", "getMeditationerrorException: ${e.message}")

            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun deleteMeditationSegmentList(
        token:String,
        jsonMap: Map<String, String>
    ): Response<TimeSegmentResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.deleteMeditationSegmentList("application/json","JWT $token",jsonMap)
            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun deleteMeditationRoutineList(
        token:String,
        jsonMap: Map<String, String>
    ): Response<MeditationRoutineSegmentResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.deleteMeditationRoutineList("application/json","JWT $token",jsonMap)
            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun activeMeditationRoutineList(
        token:String,
        jsonMap: Map<String, String>
    ): Response<MeditationRoutineSegmentResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.activeMeditationRoutineList("application/json","JWT $token",jsonMap)
            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun addTimeSegment(
        token:String,
        jsonMap: Map<String, String>
    ): Response<CommonMessageResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.addTimeSegment("application/json","JWT $token",jsonMap)
            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    suspend fun editTimeSegment(
        token:String,
        jsonMap: Map<String, String>
    ): Response<CommonMessageResponse> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.editTimeSegment("application/json","JWT $token",jsonMap)
            if (response.isSuccessful) {
                Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                Response.Error(response.code(), errorMessage?:"")
            }

        } catch (e: Exception) {
            Response.Error(0, e.message ?: "An error occurred")
        }
    }
    @SuppressLint("SuspiciousIndentation")
    suspend fun addMeditationRoutineListWithRetry(
        token: String,
        jsonMap: SaveSegmentData,
        maxRetries: Int = 3 // Maximum number of retries
    ): Response<SaveSegmentResponse> = withContext(Dispatchers.IO) {
        var retries = 0
        var lastException: Exception? = null

      //  while (retries < maxRetries) {  }
            try {
             //   ,
                val response = apiService.addMeditationRoutineList("application/json","JWT $token","application/json", jsonMap)

                if (response.isSuccessful) {

                    return@withContext Response.Success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = parseErrorMessage(errorBody)


                    return@withContext Response.Error(response.code(), errorMessage ?: "")
                }
            } catch (e: Exception) {
                Log.d("addMeditationNote", "getMeditationerrorException: ${e.message}")
                Log.d("addMeditationNote", "getMeditationerrorException: ${e.cause}")
                lastException = e
                Response.Error(0, e.message ?: "An error occurred")
              //  retries++
            }


    }
    @SuppressLint("SuspiciousIndentation")
    suspend fun getEditGroupList(
        token: String,
        groupId: Int?,
    ): Response<EditGroupListResponse> = withContext(Dispatchers.IO) {

            try {
             //   ,
                val response = apiService.getEditGroupList("application/json","JWT $token",groupId)

                if (response.isSuccessful) {

                    return@withContext Response.Success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = parseErrorMessage(errorBody)
                    return@withContext Response.Error(response.code(), errorMessage ?: "")
                }
            } catch (e: Exception) {

                Response.Error(0, e.message ?: "An error occurred")

            }


    }


    @SuppressLint("SuspiciousIndentation")
    suspend fun deleteGroup(
        token: String,
        jsonMap: Map<String, String>
    ): Response<CommonMessageResponse> = withContext(Dispatchers.IO) {

            try {
             //   ,
                val response = apiService.deleteGroup("application/json","JWT $token",jsonMap)

                if (response.isSuccessful) {
                    return@withContext Response.Success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = parseErrorMessage(errorBody)
                    return@withContext Response.Error(response.code(), errorMessage ?: "")
                }
            } catch (e: Exception) {

                Response.Error(0, e.message ?: "An error occurred")

            }


    }
    @SuppressLint("SuspiciousIndentation")
    suspend fun memberProfile(
        token: String,
        timeZone: String,
        jsonMap: Map<String, String>
    ): Response<MemberProfileResponse> = withContext(Dispatchers.IO) {

            try {

                val response = apiService.memberProfile("application/json","JWT $token",timeZone,jsonMap)

                if (response.isSuccessful) {
                    return@withContext Response.Success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = parseErrorMessage(errorBody)
                    return@withContext Response.Error(response.code(), errorMessage ?: "")
                }
            } catch (e: Exception) {

                Response.Error(0, e.message ?: "An error occurred")

            }


    }

    suspend fun getGroupStates(
        token: String,
        groupId: Int,
    ): Response<GroupStatesResponse> = withContext(Dispatchers.IO) {

        try {
            //   ,
            val response = apiService.getGroupStates("application/json","JWT $token",groupId)

            if (response.isSuccessful) {

                return@withContext Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                return@withContext Response.Error(response.code(), errorMessage ?: "")
            }
        } catch (e: Exception) {

            Response.Error(0, e.message ?: "An error occurred")

        }


    }
    suspend fun getGroupMileStone(
        token: String,
        groupId: Int,

        ): Response<GroupMilestoneResponse> = withContext(Dispatchers.IO) {

        try {
            //   ,
            val response = apiService.getGroupMileStone("application/json","JWT $token",groupId)

            if (response.isSuccessful) {
                return@withContext Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                return@withContext Response.Error(response.code(), errorMessage ?: "")
            }
        } catch (e: Exception) {
            Response.Error(0, e.message ?: "An error occurred")
        }


    }

suspend fun groupMilestoneAndGroupState(
    token: String,
    groupId: Int,
    result:(Response<GroupMilestoneResponse>, Response<GroupStatesResponse>)->Unit
){
    try {
        CoroutineScope(Dispatchers.IO).launch {
            val getGroupMileStone = async {
                getGroupMileStone(
                    token,groupId, )
            }
            val getGroupStates = async {
                    getGroupStates(token,groupId,)

            }

            val humans = getGroupMileStone.await()
            val reptiles = getGroupStates.await()


            result(humans, reptiles )




        }


    } catch (e: Exception) {



    }

}
    @SuppressLint("SuspiciousIndentation")
    suspend fun reAssign(
        token: String,
        jsonMap: Map<String, String>
    ): Response<MemberProfileResponse> = withContext(Dispatchers.IO) {

        try {

            val response = apiService.reAssign("application/json","JWT $token",jsonMap)

            if (response.isSuccessful) {
                return@withContext Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                return@withContext Response.Error(response.code(), errorMessage ?: "")
            }
        } catch (e: Exception) {

            Response.Error(0, e.message ?: "An error occurred")

        }


    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun kickOutMember(
        token: String,
        jsonMap: Map<String, String>
    ): Response<MemberProfileResponse> = withContext(Dispatchers.IO) {

        try {

            val response = apiService.kickOutMember("application/json","JWT $token",jsonMap)

            if (response.isSuccessful) {
                return@withContext Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                return@withContext Response.Error(response.code(), errorMessage ?: "")
            }
        } catch (e: Exception) {

            Response.Error(0, e.message ?: "An error occurred")

        }


    }
    @SuppressLint("SuspiciousIndentation")
    suspend fun updateRoutineName(
        token: String,
        jsonMap: RoutineNameRequest
    ): Response<ReOrderSegmentResponse> = withContext(Dispatchers.IO) {

        try {

            val response = apiService.updateRoutineName("application/json","JWT $token",jsonMap)

            if (response.isSuccessful) {
                return@withContext Response.Success(response.body()!!)
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = parseErrorMessage(errorBody)
                return@withContext Response.Error(response.code(), errorMessage ?: "")
            }
        } catch (e: Exception) {

            Response.Error(0, e.message ?: "An error occurred")

        }


    }


*/
}
