package com.nadhif.hayazee.model.dummies

import com.nadhif.hayazee.model.response.RegisterResponse

object DummyAddNewStory {

    fun getDummySuccessPostStory(): RegisterResponse{
        return RegisterResponse(
            false,
            "Story created!"
        )
    }

    fun getDummyErrorPostStory(): RegisterResponse{
        return RegisterResponse(
            true,
            "Story failed to created!"
        )
    }
}